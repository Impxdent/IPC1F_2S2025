/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica2;

/**
 *
 * @author André
 */
public class Personaje {
    private String nombre, arma;
    private int hp, ataque, velocidad, agilidad, defensa;
    
    public Personaje(String nombre, String arma, int hp, int ataque, int velocidad, int agilidad, int defensa) {
        this.nombre = nombre;
        this.arma = arma;
        this.hp = hp;
        this.ataque = ataque;
        this.velocidad = velocidad;
        this.agilidad = agilidad;
        this.defensa = defensa;
    }
    
    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    
    public String getArma() { 
        return arma; 
    }
    
    public void setArma(String arma) { 
        this.arma = arma; 
    }
    
    public int getHp() { 
        return hp; 
    }
    
    public void setHp(int hp) { 
        this.hp = hp; 
    }
    
    public int getAtaque() { 
        return ataque; 
    }
    
    public void setAtaque(int ataque) { 
        this.ataque = ataque; 
    }
    
    public int getVelocidad() { 
        return velocidad; 
    }
    
    public void setVelocidad(int velocidad) { 
        this.velocidad = velocidad; 
    }
    
    public int getAgilidad() { 
        return agilidad; 
    }
    
    public void setAgilidad(int agilidad) { 
        this.agilidad = agilidad; 
    }
    
    public int getDefensa() { 
        return defensa; 
    }
    
    public void setDefensa(int defensa) { 
        this.defensa = defensa; 
    }

    @Override
    public String toString() {
        return nombre + " (" + arma + ")";
    }
}
