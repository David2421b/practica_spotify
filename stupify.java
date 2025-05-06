import java.util.Scanner;
import java.util.concurrent.locks.LockSupport;
import java.util.Random;
import java.io.IOException;

class Double_node {

    Cancion value;
    Double_node next;
    Double_node prev;

    public Double_node(Cancion value, Double_node next, Double_node prev){
        this.value = value;
        this.next = next;
        this.prev = prev;
    }

    public void imprimir_nodo(){
        value.imprimir_cancion();
    }
}


class double_linked_list{

    Double_node head;
    Double_node tail;
    int size;
    Double_node cancion_actual;
    Long un_segundo = 1_000_000_000L;
    Long dos_segundos = 2_600_000_000L;
    Long cuatro_segundos = 4_000_000_000L;

    public double_linked_list(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void play(){
        if(this.head != null){
            cancion_actual = this.head;
        }
        
        else{
            System.out.println("Primero agrega canciones a tu playlist");
            LockSupport.parkNanos(dos_segundos);
        }
    }

    public void agregar(Cancion song){
        String progreso = "";
        Double_node new_node = new Double_node(song, null, null);
        if (this.verificar_cancion(song)){
            if (this.head == null){
                this.head = new_node;
                this.tail = new_node;
                this.size++;
            }
            
            else{
                this.tail.next = new_node;
                this.tail.next.prev = this.tail;
                this.tail = new_node;
                this.size++;
            }
            
            int porcentaje = 0;
            for (int i = 0; i < 5; i++){
                progreso += "=";
                System.out.print("\r  Descargando [" + progreso + "] " + porcentaje + "%");
                porcentaje += 25;
                LockSupport.parkNanos(un_segundo);
            }
            System.out.println("\nCANCION AGREGADA CON EXITO ;)");
            LockSupport.parkNanos(un_segundo);
        }
        
        else{
            LockSupport.parkNanos(dos_segundos);
        }
    }

    public boolean verificar_cancion(Cancion song){
        return verificar_cancion(song, this.head);
    }

    private boolean verificar_cancion(Cancion song, Double_node nodo_entrante){
        if (nodo_entrante == null){
            return true;
        }
        if (song.titulo.equals(nodo_entrante.value.titulo)){
            System.out.println("La cancion: " + song.titulo + " ya se encuentra en tu playlist\n");
            return false;
        }
        return verificar_cancion(song, nodo_entrante.next);
    }

    public Cancion buscar_cancion(String titulo){
        return this.buscar_cancion(titulo, this.head);
    }

    private Cancion buscar_cancion(String titulo, Double_node nodo_entrante){
        if(nodo_entrante == null){
            System.out.println("La cancion: " + titulo + " no se encuentra en tu playlist principal");
            return null;
        }

        if (nodo_entrante.value.titulo.equals(titulo))
            return nodo_entrante.value;
        return buscar_cancion(titulo, nodo_entrante.next);
    }

    public void avanzar(){
        if (cancion_actual.next == null){
            cancion_actual = this.head;
        }
        
        else{
            cancion_actual = cancion_actual.next;
        }
        return;
    }

    public void retroceder(){
        if (cancion_actual.prev == null){
            cancion_actual = this.tail;
        }
        else{
            cancion_actual = cancion_actual.prev;
        }
        return;
        
    }

    public void eliminar(String titulo_eliminar){
        eliminar(titulo_eliminar, this.head);
        LockSupport.parkNanos(dos_segundos);
        return;
    }

    private void eliminar(String titulo, Double_node nodo_entrante){
        
        if (nodo_entrante == null){
            System.out.println("La cancion: " + titulo + ", no se encuentra en tu Playlist");
            return;
        }
        
        if (titulo.equals(nodo_entrante.value.titulo)){
            if (nodo_entrante == this.head) {
                this.head = nodo_entrante.next;
                if (this.head != null) {
                    this.head.prev = null;
                }
            
                if (nodo_entrante == this.tail) {
                    this.tail = null;
                }
            }
            
            else if (nodo_entrante.next == null) {
                nodo_entrante.prev.next = null;
                if (this.tail != null) {
                    this.tail.next = null;
                }
                this.tail = nodo_entrante.prev;
            }
            
            else{
                nodo_entrante.prev.next = nodo_entrante.next;
                nodo_entrante.next.prev = nodo_entrante.prev;
            }
            this.size--;
            System.out.println("La cancion: " + titulo + ", ha sido eliminada con exito");
            return;
        }
        this.eliminar(titulo , nodo_entrante.next);
        return;
    }

    public void mostrar_playlist(){
        System.out.println("Tu playlist es: \n");
        this.mostrar_playlist(this.head);
        System.out.println("-----------------------------------------\n");
        LockSupport.parkNanos(cuatro_segundos);
        return;
    }

    private void mostrar_playlist(Double_node nodo_entrante){
        if (nodo_entrante == null){
            return;
        }
        
        nodo_entrante.value.imprimir_cancion();
        this.mostrar_playlist(nodo_entrante.next);
        return;
    }

    public void modo_aleatorio(){
        if (size <= 0){
            System.out.println("Primero agrega canciones a tu playlist");
            LockSupport.parkNanos(un_segundo);
            return;
        }
        Random rd = new Random();
        Integer numero_aleatorio = rd.nextInt(size);
        this.modo_aleatorio(numero_aleatorio, this.head);
    }

    private void modo_aleatorio(Integer idx, Double_node nodo_entrante){
        if (idx == 0){
            cancion_actual = nodo_entrante;
            return;
        }
        this.modo_aleatorio(idx - 1, nodo_entrante.next);
    }
}


class Cancion{
    
    String titulo;
    String artista;
    Integer duracion;

    public Cancion(String titulo, String artista, Integer duracion) {
        this.titulo = titulo;
        this.artista = artista;
        this.duracion = duracion;
    }

    public void imprimir_cancion(){
        System.out.println(this.titulo + " - " + this.artista + " (" + this.duracion + "s)\n");
    }
}


public class stupify {

    public static void limpiar_Menu() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
            }
            System.out.flush();
        } catch (Exception e) {
            System.out.println("\n".repeat(50));
        }
    }

    public static void main(String[] args) throws IOException{
        String titulo;
        String artista;
        Double porcentaje_usuario;
        Double adelanto;
        Integer porcentaje_avance;
        Integer duracion = 0;
        Integer eleccion = null;
        boolean flag_salir = true;
        boolean flag_eleccion = true;
        boolean flag_reproducir = true;
        boolean flag_play_list = true;
        Long un_segundo= 1_000_000_000L;
        Long dos_segundos= 2_600_000_000L;
        
        Scanner sc = new Scanner(System.in);
        double_linked_list play_list = new double_linked_list();
        double_linked_list sub_play_list = new double_linked_list();
        double_linked_list play_list_actual = play_list;
        
        while (flag_salir){
            limpiar_Menu();
            porcentaje_avance = 0;
            
            System.out.print("\033[1;1H");
                if (flag_play_list) {
                    System.out.println("Playlist principal");
                }
                else{
                    System.out.println("\r  Sub-Playlist");
                }
                System.out.println("\n \r🎵 Bienvenido a tu Playlist Interactiva 🎵\n");
                System.out.println("\r    1. Iniciar");
                System.out.println("\r    2. Agregar Cancion");
                System.out.println("\r    3. Avanzar a la siguiente canción");
                System.out.println("\r    4. Retroceder a la canción anterior");
                System.out.println("\r    5. Eliminar una canción");
                System.out.println("\r    6. Mostrar toda la playlist");
                System.out.println("\r    7. Activar modo aleatorio");
                System.out.println("\r    8. Adelantar una canción");
                System.out.println("\r    9. cambiar de playlist");
                System.out.println("\r    10. Salir\n");
                if(flag_eleccion && flag_reproducir){
                    System.out.print("\r  Elige una opcion: ");
                    eleccion = sc.nextInt();
                    System.out.println();
                }
            
            switch (eleccion) {
                case 1:
                    if(flag_play_list){
                        play_list_actual = play_list;
                    }
                    else{
                        play_list_actual = sub_play_list;
                    }
                    
                    play_list_actual.play();
                    
                    if(play_list_actual.cancion_actual != null){
                        flag_eleccion = false;
                    }
                    else{
                        flag_eleccion = true;
                        flag_reproducir = true;
                    }
                    break;
                
                case 2:
                    duracion = 0;
                    
                    sc.nextLine();
                    System.out.print("  Ingresa el Titulo: ");
                    titulo = sc.nextLine();
                    System.out.print("  Ingresa el Artista: ");
                    artista = sc.nextLine();
                    
                    while (duracion > 16 || duracion < 10){
                        System.out.print("  Ingresa la duracion entre (10s - 15s): ");
                        duracion = sc.nextInt();
                    }
                    
                    Cancion cancion = new Cancion(titulo, artista, duracion);
                    play_list_actual.agregar(cancion);
                    flag_reproducir = true;
                    break;
                
                case 3:
                    if (play_list_actual.cancion_actual != null) {
                        play_list_actual.avanzar();
                        flag_eleccion = false;
                        flag_reproducir = true;
                    }
                    else{
                        System.out.println("La reproduccion esta vacia");
                        play_list_actual.cancion_actual = null;
                        LockSupport.parkNanos(un_segundo);
                    }
                    break;
                
                case 4:
                    if (play_list_actual.cancion_actual != null){
                        play_list_actual.retroceder();
                        flag_eleccion = false;
                        flag_reproducir = true;
                    }
                    else{
                        System.out.println("La reproduccion esta vacia");
                        play_list_actual.cancion_actual = null;
                        LockSupport.parkNanos(un_segundo);
                    }
                    break;
                
                case 5:
                    sc.nextLine();
                    System.out.print("  Ingresa el titulo que deseas eliminar: ");
                    titulo = sc.nextLine();
                    play_list_actual.eliminar(titulo);
                    if (play_list_actual.cancion_actual != null) {
                        play_list_actual.avanzar();
                    }
                    
                    flag_eleccion = true;
                    flag_reproducir = true;
                    play_list_actual.cancion_actual = null;
                    break;
                
                case 6:
                    play_list_actual.mostrar_playlist();
                    flag_reproducir = true;
                    break;
                
                case 7:
                    if(flag_play_list){
                        play_list_actual = play_list;
                    }
                    else{
                        play_list_actual = sub_play_list;
                    }
                    
                    play_list_actual.modo_aleatorio();
                    
                    if(play_list_actual.cancion_actual != null){
                        flag_eleccion = false;
                    }
                    else{
                        flag_eleccion = true;
                        flag_reproducir = true;
                    }
                    break;
                
                case 8:
                    if (play_list_actual.cancion_actual != null) {
                        System.out.print("Ingresa el % que deseas avanzar: ");
                        porcentaje_usuario = sc.nextDouble();
                        
                        adelanto = play_list_actual.cancion_actual.value.duracion * (porcentaje_usuario / 100.0);
                        porcentaje_avance += (int) Math.round(adelanto);
                        flag_reproducir = true;
                    }
                    else{
                        System.out.println("Ninguna cancion en reproduccion");
                        LockSupport.parkNanos(un_segundo);
                    }
                    break;
                
                case 9:
                    if (flag_play_list){
                        flag_play_list = false;
                    }
                    else{
                        flag_play_list = true;
                    }
                    flag_eleccion = true;
                    flag_reproducir = true;
                    play_list_actual.cancion_actual = null;
                    break;
                
                case 10:
                    flag_salir = false;
                    break;
                
                default:
                    System.out.println("ingresa una opcion valida\n");
                    LockSupport.parkNanos(dos_segundos);
                    break;
                }
                
                if (play_list_actual.cancion_actual != null) {
                    String barra_progreso = "";
                    
                    System.out.println("=== CANCIÓN ACTUAL ===");
                    play_list_actual.cancion_actual.value.imprimir_cancion();
                    
                    for (; porcentaje_avance < play_list_actual.cancion_actual.value.duracion + 1; porcentaje_avance++){
                        barra_progreso += "=";
                        
                        System.out.print("\r" + porcentaje_avance + "/" + play_list_actual.cancion_actual.value.duracion + " [" + barra_progreso + "]");
                        
                        if (System.in.available() > 0) {
                            eleccion = sc.nextInt();
                            flag_eleccion = false;
                            flag_reproducir = false;
                            break;
                        }
                        LockSupport.parkNanos(un_segundo);
                    }
                    
                    if(flag_reproducir){
                        eleccion = 3;
                    }
                    System.out.println();
                }
            }
        sc.close();
        System.out.println("Gracias por escucharnos, Hasta pronto");
    }
}