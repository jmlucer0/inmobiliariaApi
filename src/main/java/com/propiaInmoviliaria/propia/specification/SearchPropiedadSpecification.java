package com.propiaInmoviliaria.propia.specification;

import com.propiaInmoviliaria.propia.enums.Disponibilidad;
import com.propiaInmoviliaria.propia.enums.TipoDeOperacion;
import com.propiaInmoviliaria.propia.enums.TipoDePropiedad;
import com.propiaInmoviliaria.propia.model.Direccion;
import com.propiaInmoviliaria.propia.model.Propiedad;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchPropiedadSpecification implements Specification<Propiedad> {

    private String numeroDeReferencia;
    private Long precioMax;
    private Long precioMin;
    private TipoDeOperacion tipoDeOperacion;
    private TipoDePropiedad tipoDePropiedad;
    private String direccion;
    private Disponibilidad disponibilidad;
    private Boolean cochera;
    private Boolean patio;
    private Integer banios;
    private Integer dormitorios;

    public SearchPropiedadSpecification(Pageable pageable, String numeroDeReferencia, Long precioMin, Long precioMax, TipoDeOperacion tipoDeOperacion, TipoDePropiedad tipoDePropiedad, Disponibilidad disponibilidad, Boolean cochera, Boolean patio, Integer banios, Integer dormitorios, String direccion) {
        this.numeroDeReferencia = numeroDeReferencia;
        this.precioMin = precioMin;
        this.precioMax = precioMax;
        this.tipoDeOperacion = tipoDeOperacion;
        this.tipoDePropiedad = tipoDePropiedad;
        this.disponibilidad = disponibilidad;
        this.cochera = cochera;
        this.patio = patio;
        this.banios = banios;
        this.dormitorios = dormitorios;
        this.direccion = direccion;

    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(numeroDeReferencia)){
            Predicate likeNumeroDeReferencia = criteriaBuilder.like(root.get("numeroDeReferencia"), "%" + numeroDeReferencia +"%");
            predicates.add(likeNumeroDeReferencia);
        }

        if (precioMin != null && precioMax != null) {
            System.out.println("PrecioMin: " + precioMin + ", PrecioMax: " + precioMax);
            Predicate rangoPrecioPredicate = criteriaBuilder.between(root.get("precio"), precioMin, precioMax);
            predicates.add(rangoPrecioPredicate);
        } else if (precioMin != null) {
            Predicate precioMinPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("precio"), precioMin);
            predicates.add(precioMinPredicate);
        } else if (precioMax != null) {
            Predicate precioMaxPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("precio"), precioMax);
            predicates.add(precioMaxPredicate);
        }

        if (tipoDeOperacion != null){
            Predicate tipoDeOperacionPredicate = criteriaBuilder.equal(root.get("tipoDeOperacion"), tipoDeOperacion.name());
            predicates.add(tipoDeOperacionPredicate);
        }
        if (tipoDePropiedad != null){
            Predicate tipoDePropiedadPredicate = criteriaBuilder.equal(root.get("tipoDePropiedad"), tipoDePropiedad.name());
            predicates.add(tipoDePropiedadPredicate);
        }
        if (disponibilidad != null){
            Predicate disponibilidadPredicate = criteriaBuilder.equal(root.get("disponibilidad"), disponibilidad.name());
            predicates.add(disponibilidadPredicate);
        }
        if (cochera != null){
            Predicate cocheraPredicate = criteriaBuilder.equal(root.get("cochera"), cochera);
            predicates.add(cocheraPredicate);
        }
        if (patio != null){
            Predicate patioPredicate = criteriaBuilder.equal(root.get("patio"), patio);
            predicates.add(patioPredicate);
        }
        if (banios != null && banios > 0){
            Predicate baniosPredicate = criteriaBuilder.equal(root.get("banios"), banios);
            predicates.add(baniosPredicate);
        }
        if(dormitorios != null && dormitorios > 0){
            Predicate dormitoriosPredicate = criteriaBuilder.equal(root.get("dormitorios"), dormitorios);
            predicates.add(dormitoriosPredicate);
        }
        if (direccion != null){
            System.out.println(direccion);
            Join<Propiedad, Direccion> propiedadDireccionJoin = root.join("direccion");
            Expression<String> calleToLowerCase = criteriaBuilder.lower(propiedadDireccionJoin.get("calle"));
            Predicate likeCallePredicate = criteriaBuilder.like(calleToLowerCase, "%".concat(direccion).concat("%"));
            predicates.add(likeCallePredicate);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
