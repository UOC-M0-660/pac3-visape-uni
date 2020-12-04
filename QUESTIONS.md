# PARTE TEORICA

### Lifecycle

#### Explica el ciclo de vida de una Activity.

##### ¿Por qué vinculamos las tareas de red a los componentes UI de la aplicación?
Las tareas de red se vinculan a los componentes UI de la aplicación para que cuando el usuario cierre la pantalla, es decir, cuando la activity muera, se cancelen las tareas de red y evite realizar tareas que ya no son necesarias.

##### ¿Qué pasaría si intentamos actualizar la recyclerview con nuevos streams después de que el usuario haya cerrado la aplicación?
Saltaría un error, ya que al estar cerrada la aplicación el recyclerview ya no existiría i al intentar actualizarlo petaría por NullPointer o otra Exception.

##### Describe brevemente los principales estados del ciclo de vida de una Activity.
Cuando una activity se lanza pasa por una serie de estados que llaman diferentes métodos:
1. OnCreate(): És el primer estado de la Activity, este método se llama cuando se lanza la activity.
1. OnStart(): És el siguiente estado después del create y corresponde al momento justo antes de que la activity se vuelva visible al usuario.
1. OnResume(): És el punto en el que la activity pasa a estar en primer plano y se vuelve visible al usuario.
1. Running: És cuando la activity está ejecutándose y está en primer plano.
1. OnPause(): És cuando la activity pierde el foco o deja de estar en primer plano. De este estado puede volver al OnResume() (si la activity vuelve a estar en primer plano) o al siguiente estado que es OnStop() (cuando la activity deja de estar visible).
1. OnStop(): És cuando la activity deja de ser visible. De este estado puede ir al OnRestart() (si la activity vuelve a ser visible para el usuario) o al siguiente estado que es OnDestroy() (si la actividad es destruida).
1. OnRestart(): És llamado después del OnStop() si el usuario vuelve a poner la activity en primer plano.
1. OnDestroy(): És llamado cuando el usuario elige dar por finalizada la actividad. Permite liberar los recursos que ha estado utilizando la actividad.

Estos son los métodos que se llaman en cada cambio de estado. Los estados de una activity son los siguientes:

1. Inexistente: És cuando la activity aún no existe.
1. Detenida: És cuando la activity está en memoria pero no es visible para el usuario.
1. Pausada: És cuando la activity está en memoria, y es visible parcialmente por el usuario, pero no está en primer plano.
1. En ejecución: És cuando la acitvity está en memoria, y es visible en primer plano por el usuario.

---

### Paginación 

#### Explica el uso de paginación en la API de Twitch.

##### ¿Qué ventajas ofrece la paginación a la aplicación?
La principal ventaja que ofrece la paginación és que permite comenzar a mostrar la información sin necesidad de cargar toda la información. Por lo tanto proporciona más velocidad. El resto de información se cargará en caso de ser necesaria. Además también reduce el uso de recursos como la memoria.

##### ¿Qué problemas puede tener la aplicación si no se utiliza paginación?
Si no se usa paginación podría no disponer de suficientes recursos para cargar toda la información de golpe y dar un error. Además, en el caso de disponer de suficientes recursos, podría aumentar significativamente el tiempo de carga de la información.

##### Lista algunos ejemplos de aplicaciones que usan paginación.
Hay muchas aplicaciones que usan paginación. Prácticamente todas las aplicaciones de redes sociales:

* Youtube
* Twitter
* Instagram
* Facebook
