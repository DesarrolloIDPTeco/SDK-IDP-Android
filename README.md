# SDK IDP Android

Funcionalidades
* Inicio/LogIn
    El APK implementa el login propio del SDK.

* Cierre de sesion/LogOut
    Se implemento un boton el cual hace la llamada a la funcion propia del SDK para lograr, exitosamente, el cierre de sesion.

* Refresh Token
    Si bien el SDK de Forgerock realiza automaticamente el Refresh, SOLAMENTE si ha vencido el JWT, se implementó un boton para que "recargue" la pantalla mostrando el nuevo JWT, Access Token, etc.
    El boton solamente aparece y funciona, luego de que el Access Token haya vencido. Es decir, luego que hayan pasado la cantidad de segundos que aparecen como valor del **expiresIn**

Pruebas
* Relacion al inicio de sesion
    * Iniciada sesion, minimizado y cerrado la aplicacion, vuelvo a abrir la apk ( Probado en los tiempos de 2min a 1 hora cerrada la APK), vuelvo abrir el APK, mi sesion sigue iniciada y doy al boton de Refresh y se actualizan los datos en el APK correctamente.


* Relacion al cierre de sesion
    * Iniciada sesion, cerrada la aplicacion. Elimine la sesion desde la consola web del AM. Volvi abrir la aplicacion y me pidio las credenciales nuevamente.
