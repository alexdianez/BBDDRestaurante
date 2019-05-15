/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "ESTABLECIMIENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Establecimiento.findAll", query = "SELECT e FROM Establecimiento e")
    , @NamedQuery(name = "Establecimiento.findByIdEstab", query = "SELECT e FROM Establecimiento e WHERE e.idEstab = :idEstab")
    , @NamedQuery(name = "Establecimiento.findByNombre", query = "SELECT e FROM Establecimiento e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Establecimiento.findByDue\u00f1o", query = "SELECT e FROM Establecimiento e WHERE e.due\u00f1o = :due\u00f1o")
    , @NamedQuery(name = "Establecimiento.findByEspacio", query = "SELECT e FROM Establecimiento e WHERE e.espacio = :espacio")
    , @NamedQuery(name = "Establecimiento.findByFechaCreacion", query = "SELECT e FROM Establecimiento e WHERE e.fechaCreacion = :fechaCreacion")})
public class Establecimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ESTAB")
    private Integer idEstab;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "DUE\u00d1O")
    private String dueño;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ESPACIO")
    private BigDecimal espacio;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @OneToMany(mappedBy = "establecimiento")
    private Collection<Empleado> empleadoCollection;

    public Establecimiento() {
    }

    public Establecimiento(Integer idEstab) {
        this.idEstab = idEstab;
    }

    public Establecimiento(Integer idEstab, String nombre, String dueño) {
        this.idEstab = idEstab;
        this.nombre = nombre;
        this.dueño = dueño;
    }

    public Integer getIdEstab() {
        return idEstab;
    }

    public void setIdEstab(Integer idEstab) {
        this.idEstab = idEstab;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDueño() {
        return dueño;
    }

    public void setDueño(String dueño) {
        this.dueño = dueño;
    }

    public BigDecimal getEspacio() {
        return espacio;
    }

    public void setEspacio(BigDecimal espacio) {
        this.espacio = espacio;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @XmlTransient
    public Collection<Empleado> getEmpleadoCollection() {
        return empleadoCollection;
    }

    public void setEmpleadoCollection(Collection<Empleado> empleadoCollection) {
        this.empleadoCollection = empleadoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstab != null ? idEstab.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Establecimiento)) {
            return false;
        }
        Establecimiento other = (Establecimiento) object;
        if ((this.idEstab == null && other.idEstab != null) || (this.idEstab != null && !this.idEstab.equals(other.idEstab))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restaurante.Establecimiento[ idEstab=" + idEstab + " ]";
    }
    
}
