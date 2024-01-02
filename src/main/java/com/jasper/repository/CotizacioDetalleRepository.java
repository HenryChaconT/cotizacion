package com.jasper.repository;

import com.jasper.entity.CotizacionDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CotizacioDetalleRepository extends JpaRepository<CotizacionDetalle,Long> {
}
