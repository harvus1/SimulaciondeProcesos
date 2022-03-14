# Simulacion de Procesos

Programa para simular:

**Memoria principal**
                
* De tamaño N (establecido por el equipo) usando bloques de X tamaño
(establecido por el equipo), todo en base hexadecimal, con un espacio reservado
para el sistema operativo y otro espacio reservado para el activador

* Se debe mostrar gráficamente cada proceso cargado en memoria principal,
especificando el espacio de direcciones asignado a cada proceso

* Cuando un proceso termina de ser ejecutado, este debe ser quitado de la
memoria principal de manera que deje un espacio libre para un nuevo proceso

**Agregar procesos**
* Se crearán procesos en los espacios libres de la memoria principal, cada proceso
tendrá un tamaño aleatorio al momento de ser creado, por lo que se debe buscar
un espacio que abarque todo el agrupamiento de recursos de manera continua.
* El tamaño representará el tiempo de consumo del proceso
* Si bien el proceso tendrá un tamaño aleatorio, debe tener un mínimo y un máximo
en el tiempo de consumo, pero tenga en cuenta este tema para el cálculo del
quantum
* Si no hay suficiente espacio se debe indicar por medio de un cuadro de dialogo
que no hay suficiente espacio en memoria principal para el proceso
* El activador siempre tendrá el mismo tiempo de consumo

**Contador de programa (Program counter)**
* Mostrar la dirección en memoria de la instrucción en curso del proceso elegido
por el planificador
* Cada instrucción ejecutada será a razón de 1s real, es decir, 1 ms será
representado por 1 s real
* Mostrar en la memoria principal que instrucción se está ejecutando.
* Tomar los registros base y límite como el inicio y fin del espacio de direcciones
asociado al proceso que está ejecutándose
* El activador también debe aparecer cuando este efectúe la conmutación de
procesos

**Reloj de sistema**
* Hora del sistema a razón de 1s real.

**Planificación por turno circular**
* Establecer un quantum, a razón de 1s real
* La duración de cada proceso (tiempo de ejecución) se establecerá en la creación
del proceso, por lo que es de duración aleatoria y el rango lo definirá cada equipo
* El tiempo de consumo de cada proceso debe ser siempre más grande que el
tiempo de consumo del activador
* Conmutación de procesos
Se debe mostrar el bloque del activador al momento de cambiar de un proceso a otro, la
duración quedará a discreción del equipo
* Historial de procesos
Hora de inicio de cada proceso y hora de finalización de cada proceso