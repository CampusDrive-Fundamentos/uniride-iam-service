Feature: Registro de usuario como conductor
 Como conductor interesado
 Quiero ingresar mi número de DNI y subir mi Certificado Único Laboral (CUL)
 Para que la plataforma valide mi historial y me permita ofrecer servicios de forma segura

Scenario: Verificación de identidad y antecedentes para conductores
 Given que el usuario completó correctamente sus nombres, contraseña y adjuntó una foto clara de su carnet
 When envía la solicitud de registro final
 Then el sistema encripta sus datos, crea su perfil y le da acceso a la pantalla principal.

Examples:
 | Campo                  | Valor Mostrado al Usuario                                                   | 
 | Estado de Verificación | Perfil Conductor Aprobado                                                   | 
 | Mensaje                | Documentos validados. Ahora procede a registrar los detalles de tu vehículo |