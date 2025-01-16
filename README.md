# Microservicio de Transacciones (Web FLux-Reactive) del Proyecto Bancario Sofbank 

## Descripción General
Este proyecto está compuesto por dos módulos principales:

1. **Aplicación Bancaria:** Gestiona cuentas bancarias y actualiza saldos mediante transacciones en una base de datos SQL, este se encuentra en el respositorio: https://github.com/julianest/SofBankSpring.
2. **Microservicio de Transacciones:** Realiza operaciones de transacciones financieras y persiste la información en MongoDB (NoSQL).

Ambos módulos se comunican mediante llamadas HTTP.

---

## Clases Principales

### Aplicación Bancaria

#### Controlador

```java
@PutMapping("/update/fromMicros/{numberAccount}")
public ResponseEntity<BankAccount> updateAccountFromMicroservice(
    @PathVariable String numberAccount,
    @RequestBody TransactionRequestDTO requestDTO
) {
    // Lógica para comunicarse con el microservicio
}
```

#### Servicio
```java
public interface BankAccountService {
    BankAccount updateBalance(String numberAccount, TransactionRequestDTO requestDTO);
}

@Service
public class BankAccountServiceImpl implements BankAccountService {
    @Override
    public BankAccount updateBalance(String numberAccount, TransactionRequestDTO requestDTO) {
        // Actualización de saldo en la base de datos H2
    }
}
```

### DTO de Transacción
```java
@Getter
@Setter
public class TransactionRequestDTO {
    private double amount;
    private TypeTransaction typeTransaction;
    private TypeAccount typeAccount;
}
```

### Fábrica de Servicios
```java
@Component
public class BankAccountServiceFactory {
    public BankAccountService getService(TypeAccount typeAccount) {
        // Retorna la implementación correspondiente según el tipo de cuenta
    }
}
```

---

### Microservicio de Transacciones

#### Controlador
```java
@PostMapping("/transact/process")
public Mono<TransactionRequestDTO> processTransaction(
    @RequestHeader HttpHeaders headersRequest,
    @RequestBody Mono<TransactionRequestDTO> transactionRequestDTO
) {
    // Lógica para procesar la transacción
}
```

#### Servicio de Autenticación
```java
@Service
public class AuthService {
    public <T> T addCreatedByAuditInfo(T obj, String authName) {
        // Lógica para setear información de auditoría
    }

    public <T> T addUpdatedByAuditInfo(T obj, String authName) {
        // Lógica para setear información de auditoría
    }
}
```

#### Servicio de Transacciones
```java
@Service
public class TransactionService {
    public Mono<TransactionRequestDTO> processTransaction(HttpHeaders headersRequest, String numberAccount, Mono<TransactionRequestDTO> transactionRequestDTO) {
        // Procesamiento reactivo de transacciones
    }
}
```

#### Repositorio
```java
@Repository
public interface TransactionRequestRepository extends ReactiveMongoRepository<TransactionRequest, String> {
}
```
## Diagrama de clases:
![Diagrama de clases.png](src/main/resources/Diagrama%20de%20clases.png)
---

## Flujo del Proyecto
1. **Inicia la Aplicación Bancaria:**
    - Recibe una solicitud de transacción desde un cliente.
    - Envía la solicitud al Microservicio de Transacciones.

2. **Microservicio de Transacciones:**
    - Procesa la transacción de manera reactiva.
    - Actualiza los datos en MongoDB.
    - Devuelve el resultado a la Aplicación Bancaria.

3. **Aplicación Bancaria:**
    - Recibe los datos actualizados y persiste el nuevo saldo en H2.

![Descripcion Flujo Aplicaciones.png](src/main/resources/Descripcion%20Flujo%20Aplicaciones.png)

---

## Prerrequisitos
- **Aplicación Bancaria:**
    - Ver repositorio de aplicacion.

- **Microservicio de Transacciones:**
    - Java 17
    - Spring Boot WebFlux
    - MongoDB
    - Lombok

---

## Configuración

### Propiedades del Microservicio
```properties
server.port=8085
spring.data.mongodb.uri=MONGOCONNECT
```
Se utiliza un archivo .env para generarla conexion segura y mostrar credenciales.

### Propiedades de la Aplicación Bancaria
```properties
server.port=8080
```
Ver repositorio de la app bancaria.

---

## Cómo Ejecutar

1. Inicia el servicio de MongoDB.
2. Arranca primero el Microservicio de Transacciones.
3. Luego, inicia la Aplicación Bancaria.
4. Realiza una solicitud al endpoint de la Aplicación Bancaria para actualizar saldos.

---

## POSTMAN
Colecciones:
[SofkaBank.postman_collection.json](src/main/resources/SofkaBank.postman_collection.json)

---

## SWAGGER
- Revisar Documentacion de los controllers en el siguiente enlace:
  http://localhost:8085/webjars/swagger-ui/index.html

---

## TODO
- Mejorar la cobertura de pruebas unitarias.
- Mejorar refactorización de codigo.
- Agregar Licencia

---

## Autor
**Julian Steven Huerfano.**

---

## Contribuciones
Si deseas contribuir, por favor crea un fork del repositorio y envía un pull request con tus cambios.

---

## Licencia
Este proyecto está bajo la Licencia MIT. Puedes consultar el archivo LICENSE para más detalles.
