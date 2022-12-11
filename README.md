# MuseoApp
App Museo - PAMN

## Introducción
Esta aplicación es una Demo para un Museo, con la idea de que todos los usuarios que estén o no en el museo, pueda ver y disfrutar de las distintas obras que tiene,
obtener información del propio museo y tener acceso a una otras funciones incluidas para los usuarios que están registrados en la app.

### Esta aplicación utiliza FireBase que nos provee de diversas características:

- Authentication: La cual registra los usuarios mediante email / password y nos genera una key
- Realtime Database: La utilizamos para almacenar las distintas estructuras de datos, como son los detalles de los usuarios y el de las obras del museo.
- Storage: Se almacenan en distintas carpetas, las imágenes de las obras, de los usuarios y los audios que están asociados a las obras.

### Características principales de la App:
- Lector de códigos QR: Puedes escanear los códigos QR en el museo y visualizar de forma detallada la obra, sin necesidad de buscarla.
- Audio: Si esa obra tiene una descripción en audio, puedes reproducirla, pulsando en el botón play que aparece en el app bar.
- Ubicación en tiempo real: La aplicación provee de un mapa de Google, el cual tiene marcada la ubicación del museo y la tuya para guiarte.
- Roles de usuario: En función del usuario que haga inicia sesión, la vista que se muestra es distinta, para proveer las herramientas necesarias
a los distintos usuarios (Administrador y Usuario Registrado)

  - Usuario Registrado:
    - Favoritos: Si eres un usuario de tipo "Usuario Registrado" y has iniciado sesión, tienes la posibilidad de guardar tus obras favoritas, por cada obra,
en el app bar, te aparece una estrella, marcada complentamente si lo tienes como favorito o con el contorno en caso de que no. En tu perfil, tienes la posibilidad
de visualizar todo tu listado.

  - Administrador:
    - Cámara: Si eres un usuario de tipo "Administrador", tienes la posibilidad de utilizar la cámara directamente del dispositivo para crear nuevas entradas de obras.
    
- Vista detallada: Cada entrada que hay en el "Home", tiene una vista detallada donde puedes tener acceso a la descripción total de la obra.

## Roadmap
- [Codelabs](https://developer.android.com/courses/android-basics-kotlin/course): Para iniciarnos en el desarrollo Android y dar nuestros primeos pasos.
- [MVVM](https://cursokotlin.com/mvvm-en-android-con-kotlin-livedata-y-view-binding-android-architecture-components/): Para entender el MVVM que utilizamos como
arquitectura, nos guiamos por los ejemplos que podemos ver en cursokotlin.com
- [Firebase](https://firebase.google.com/docs/build?hl=es-419): Toda la información relacionada con Firebase, para cualquiera de las características que utiliza.
  - [Realtime Database Android](https://firebase.google.com/docs/database/android/start?hl=es-419): Toda la información relacionada con Database Realtime para Android,
  donde tenemos nuestros dos objetos ("users" y "gallery") y guardamos la distinta indormación relacionada con ellos.
  - [Authentication Android](https://firebase.google.com/docs/auth/android/start?hl=es-419): Toda la información utilizada para entender y realizar el login y registro
  de la App para que generara un usuario en Authentication.
  - [Storage Android](https://firebase.google.com/docs/storage/android/start?hl=es-419): Toda la información para implementar en Android Storage, el cual utilizamos
  para almacenar las fotos de los usuarios, las obras y los audios de cada obra, todo en distintas carpetas.
- [MediaPlayer Android](https://developer.android.com/guide/topics/media/mediaplayer): Guía oficial de Android, para aprender a utilizar el reproductor de Android,
en nuestro caso, vía Streaming, conectándonos a Firebase y descargando el audio de la obra a la vez que se reproduce.
- [Lector QR Android](https://www.youtube.com/watch?v=Wdb5okugwmU&ab_channel=Programaci%C3%B3nAndroidbyAristiDevs): Vídeo de AristiDevs, donde nos muestra cómo crear
un lector QR en Android, utilizando la libreía Zxing
- [CameraX Android](https://developer.android.com/codelabs/camerax-getting-started?hl=es-419#0): Codelab de ejemplo para utilizar CameraX, donde nos guía y enseña
como crear los permisos, la vista de la cámara y como almacenar en la memoria interna del dispositivo la foto.
- [Abrir galería de fotos en Android](https://www.youtube.com/watch?v=EiQn3zVlPtQ&ab_channel=Lacuevadelprogramador): Un video tutorial en el que se nos enseña con un
ejemplo muy sencillo, abrir la galería de fotos para seleccionar una y pintarla / tener su referencia, además muestra la manera de gestionar los permisos de cámara.
- [Tratar imágenes online con Glide](https://bumptech.github.io/glide/): Utilizamos la librería de Glide, para cargar y pintar en un ImageView las imágenes que tenemos
vía URL, tanto las de Firebase como las que sean de otros sitios de internet.
- [Carrousel de imágenes](https://github.com/sparrow007/CarouselRecyclerview): Guía para utilizar la librería de Sparrow, para crear un carrousel en Android.
- [Google Maps Android](https://cursokotlin.com/capitulo-30-google-maps-en-android-con-kotlin-parte-2-localizacion-en-tiempo-real-kotlin/): Guía para aprender a
utilizar el Google Maps y mostrarlo dentro de un fragment, nos enseñan a utilizar los permisos, así como a utilizar la ubicación en tiempo real del usuario y crear
un "Maker" para tener una marca roja con la ubicación establecida.
- [Reestablecer contraseña](https://firebase.google.com/docs/auth/android/manage-users#send_a_password_reset_email): Guía de Firebase para enviar un correo electrónico
para reestablecer la contraseña del usuario.
- [Cuadro de diálogos](https://developer.android.com/guide/topics/ui/dialogs?hl=es-419): Documentación oficial de Android para crear y gestionar los cuadros de
diálogos en Android, el cual lo utilizamos para enviar un correo para reestablecer la contraseña del usuario.

## Librerías:
- [Zxing QR Code](https://github.com/journeyapps/zxing-android-embedded): Lector QR Android.
- [Carrousel Android](https://github.com/sparrow007/CarouselRecyclerview): Implementación de un carrousel Android con RecyclerView.
- [Glide](https://bumptech.github.io/glide/): Utilizada para cargar las imágenes vía URL.
- [Camera X](https://developer.android.com/training/camerax?hl=es-419): Documentación de Camera X android.
- [Google Maps](https://developers.google.com/maps/documentation/android-sdk/start): Documentación oficial Google Maps android.
