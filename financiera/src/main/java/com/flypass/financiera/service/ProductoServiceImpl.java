package com.flypass.financiera.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flypass.financiera.model.Cliente;
import com.flypass.financiera.model.Estado;
import com.flypass.financiera.model.Producto;
import com.flypass.financiera.model.TipoProducto;
import com.flypass.financiera.repository.ClienteRepository;
import com.flypass.financiera.repository.EstadoRepository;
import com.flypass.financiera.repository.ProductoRepository;
import com.flypass.financiera.repository.TipoProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    @Autowired
    private EstadoService estadoService;

    @Override
    public Producto crearProducto(Producto producto) {
        
        validarClienteAsociado(producto);
        validarTipoProducto(producto);        
        validarEstado(producto);        
        asignarNumeroCuenta(producto);
        asignarEstadoPorDefecto(producto);
        producto.setSaldo(producto.getSaldo());
        producto.setExentaGMF(producto.getExentaGMF());

        return productoRepository.save(producto);
    }

    private void validarTipoProducto(Producto producto) {
        TipoProducto tipoProducto = tipoProductoRepository.findById(producto.getTipoProducto().getId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de producto no encontrado."));
        // Verificar que el tipo de producto sea cuenta corriente o cuenta de ahorros
        if (!tipoProducto.getNombre().equalsIgnoreCase("Corriente") &&
            !tipoProducto.getNombre().equalsIgnoreCase("Ahorros")) {
            throw new IllegalArgumentException("El tipo de producto debe ser 'cuenta corriente' o 'cuenta de ahorros'.");
        }
        producto.setTipoProducto(tipoProducto);
    }

    private void validarEstado(Producto producto) {
        if (producto.getEstado() == null || producto.getEstado().getId() == null) {
            throw new IllegalArgumentException("El producto debe tener un Estado");        }

        Estado estado = estadoRepository.findById(producto.getEstado().getId())
                .orElseThrow(() -> new IllegalArgumentException("Estado no encontrado."));
        // Verificar que los estados sean los correctos
        if (!estado.getNombre().equalsIgnoreCase("Activo") &&
            !estado.getNombre().equalsIgnoreCase("Inactiva") && 
            !estado.getNombre().equalsIgnoreCase("Cancelada")) {
            throw new IllegalArgumentException("Solo se permiten estados 'Activo', 'Inactivo' o 'Cancelado'.");
        }
        producto.setEstado(estado);
    }

    private void validarClienteAsociado(Producto producto) {
        Cliente cliente = producto.getCliente();
        
        if (producto.getCliente() == null || producto.getCliente().getId() == null) {
            throw new IllegalArgumentException("El producto debe estar vinculado a un cliente.");
        }
        // Verificar si el cliente existe en la base de datos
        Cliente clienteExistente = clienteRepository.findById(cliente.getId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado."));
        // Asignar el cliente existente al producto
        producto.setCliente(clienteExistente);
    }

    @Override
    public Producto actualizarProducto(Long id, Producto productoDetalles) {
        Producto producto = obtenerProductoPorId(id);

        validarClienteAsociado(productoDetalles);
        validarTipoProducto(productoDetalles);        
        validarEstado(productoDetalles);  

        producto.setSaldo(productoDetalles.getSaldo());
        producto.setExentaGMF(productoDetalles.getExentaGMF());       
                
        return productoRepository.save(producto);
    }

    @Override
    public void eliminarProducto(Long id) {
        Producto producto = obtenerProductoPorId(id);
        productoRepository.delete(producto);
    }

    @Override
    public Producto obtenerProductoPorId(Long id) {
        Optional<Producto> optionalProducto = productoRepository.findById(id);
        if (optionalProducto.isEmpty()) {
            throw new IllegalArgumentException("Producto no encontrado.");
        }
        return optionalProducto.get();
    }

    @Override
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    @Override
    public String consultarEstadoProducto(Long idProducto) {
        Optional<Producto> optionalProducto = productoRepository.findById(idProducto);
        if (optionalProducto.isPresent()) {
            Producto producto = optionalProducto.get();
            Estado estado = producto.getEstado();
            if (estado != null) {
                return estado.getNombre(); 
            } else {
                return "Estado no definido";
            }
        } else {
            throw new IllegalArgumentException("Producto no encontrado.");
        }
    }

    private void asignarNumeroCuenta(Producto producto) {
        Random random = new Random();
        String prefijo = producto.getTipoProducto().getNombre().equalsIgnoreCase("Ahorros") ? "53" : "33";
        String numeroCuenta = prefijo + String.format("%08d", random.nextInt(100000000));
        producto.setNumeroCuenta(numeroCuenta);
    }

    private void asignarEstadoPorDefecto(Producto producto) {
        if (producto.getTipoProducto().getNombre().equalsIgnoreCase("Ahorros")) {
            Estado estadoActivo = estadoService.getEstadoActivo();
            producto.setEstado(estadoActivo);
        }
    }

    @Override
    public Producto activarProducto(Long id) {
        Producto producto = obtenerProductoPorId(id);
        //validarSaldoParaActivacion(producto);
        Estado estadoActivo = estadoService.getEstadoActivo();
        producto.setEstado(estadoActivo);
        return productoRepository.save(producto);
    }

    @Override
    public Producto inactivarProducto(Long id) {
        Producto producto = obtenerProductoPorId(id);
        Estado estadoInactivo = estadoRepository.findByNombre("Inactivo")
                .orElseThrow(() -> new IllegalArgumentException("Estado 'Inactivo' no encontrado."));
        producto.setEstado(estadoInactivo);
        return productoRepository.save(producto);
    }

    @Override
    public void cancelarProducto(Long id) {
        Producto producto = obtenerProductoPorId(id);
        validarSaldoParaCancelacion(producto);
        productoRepository.delete(producto);
    }

    private void validarSaldoParaCancelacion(Producto producto) {
        if (producto.getSaldo().compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("Solo se pueden cancelar cuentas con saldo igual a $0.");
        }
    }

}
