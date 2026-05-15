Feature: Registro de usuario rol estudiante universitario
 Como usuario nuevo
 Quiero llenar mis datos personales y subir la foto de mi TIU
 Para crear oficialmente mi cuenta en la aplicación y poder buscar rutas

Scenario: Creación exitosa de perfil estudiantil con carnet universitario
 Given que el usuario completó correctamente sus nombres, contraseña y adjuntó una foto clara de su carnet
 When envía la solicitud de registro final
 Then el sistema encripta sus datos, crea su perfil y le da acceso a la pantalla principal.

Examples:
 | Campo               | Valor Mostrado al Usuario                                               | 
 | Estado de la cuenta | ¡Registro exitoso!                                                      | 
 | Mensaje             | Bienvenido a CampusDrive. Ya puedes empezar a buscar viajes a tu campus |