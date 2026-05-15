Feature: Validación de Correo Institucional
 Como estudiante
 Quiero registrarme usando mi cuenta de la universidad
 Para garantizar que la plataforma sea exclusiva y segura para la comunidad académica

Scenario: Filtro de seguridad por dominio de correo electrónico
 Given que un usuario intenta registrarse escribiendo un correo como "usuario@gmail.com"
 When presiona el botón para continuar con el registro
 Then el sistema detecta que no es un dominio válido y bloquea el avance del formulario.

Examples:
 | Campo               | Valor Mostrado al Usuario                                                          | 
 | Error en formulario | Formato de correo inválido                                                         | 
 | Mensaje             | Por favor, utiliza un correo institucional válido terminado en ".edu" o ".edu.pe". |