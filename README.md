# CompuWork - Sistema de Gestión de Recursos Humanos
**Desarrollado por: Miguelit000**

CompuWork es una aplicación de escritorio desarrollada en **Java** aplicando los principios de la Programación Orientada a Objetos (POO). El sistema permite gestionar departamentos, administrar empleados (permanentes y temporales), y generar reportes de desempeño de manera dinámica utilizando una interfaz gráfica intuitiva construida con **Java Swing**.

## 🚀 Características Principales
* **Gestión de Departamentos:** Crear, modificar, eliminar y listar departamentos.
* **Gestión de Empleados:** Registro de empleados con cálculo de compensación polimórfico (salario + bonos o horas extras).
* **Asignaciones Dinámicas:** Traslado de empleados entre departamentos en tiempo real.
* **Reportes:** Generación de reportes de desempeño individuales y por departamento.
* **Seguridad y Robustez:** Manejo de excepciones personalizadas para proteger la integridad de los datos.

---

## 📋 Requisitos Previos
Para poder ejecutar y probar este proyecto en tu máquina, necesitas:
1. Tener instalado **Java Development Kit (JDK)** (Versión 8 o superior).
2. Un entorno de desarrollo (IDE) recomendado: **Visual Studio Code**.
3. *Para VS Code:* Tener instaladas las extensiones **"Extension Pack for Java"** y **"Test Runner for Java"**.

---

## 💻 Cómo ejecutar el programa (Interfaz Gráfica)

### Opción 1: Desde Visual Studio Code (Recomendado)
1. Abre la carpeta raíz del proyecto (`CompuWork`) en Visual Studio Code.
2. Navega hasta la carpeta `src` y abre el archivo **`CompuWorkGUI.java`**.
3. Haz clic en el botón de **"Run"** (o el ícono de "Play" ▶️) que aparece en la esquina superior derecha del editor, o justo encima del método `public static void main`.
4. ¡Listo! Se abrirá la ventana del panel de control de CompuWork.

### Opción 2: Desde la Terminal (Consola)
1. Abre tu terminal y navega hasta la carpeta `src` del proyecto:
   ```bash
   cd ruta/a/tu/proyecto/CompuWork/src
   ```

2. Compila todos los archivos java:
   ```bash
   javac *.java
   ```

3. Ejecuta la interfaz principal:
   ```bash
   java CompuWorkGUI
   ```
## 🧪 Cómo ejecutar las Pruebas Unitarias (JUnit)
El sistema cuenta con una suite de pruebas automatizadas para validar los cálculos financieros y las reglas de negocio.

1. Abre el proyecto en Visual Studio Code.

2. Ve al archivo CompuWorkTests.java dentro de la carpeta src.

3. Haz clic en el botón "Run Tests" (Ejecutar pruebas) que aparece justo encima de la declaración de la clase public class CompuWorkTests.

4. Los resultados se mostrarán en el panel de Testing (ícono de matraz) en la barra lateral izquierda, confirmando que la lógica matemática y las excepciones funcionan al 100% (✅).
