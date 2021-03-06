CREATE TABLE establecimiento(
    ID_estab INTEGER GENERATED ALWAYS AS IDENTITY,
    NOMBRE varchar(20) NOT NULL,
    DUEÑO varchar(20) NOT NULL,
    ESPACIO DECIMAL(5,2),
    FECHA_CREACION date,
    CONSTRAINT ID_ESTABLECIMIENTO_PK PRIMARY KEY(ID_estab)
);
CREATE TABLE empleado (
    ID_empleado INTEGER GENERATED ALWAYS AS IDENTITY,
    NOMBRE varchar(20) NOT NULL,
    APELLIDOS VARCHAR(20) NOT NULL,
    SALARIO DECIMAL(7,2),
    FUNCION varchar(20),
    FECHA_NACIMIENTO DATE,
    establecimiento INTEGER,
    CONSTRAINT ID_EMPLEADO_PK PRIMARY KEY(ID_empleado),
    CONSTRAINT ID_ESTABLECIMIENTO_FK FOREIGN KEY (establecimiento) REFERENCES establecimiento (ID_estab)
);