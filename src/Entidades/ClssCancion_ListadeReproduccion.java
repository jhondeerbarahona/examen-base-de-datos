/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author Usuario
 */
public class ClssCancion_ListadeReproduccion {
    private String Cancion_IdCancion;
    private String ListadeReproduccion_idListadeReproduccion;

    public ClssCancion_ListadeReproduccion() {
    }

    public ClssCancion_ListadeReproduccion(String Cancion_IdCancion, String ListadeReproduccion_idListadeReproduccion) {
        this.Cancion_IdCancion = Cancion_IdCancion;
        this.ListadeReproduccion_idListadeReproduccion = ListadeReproduccion_idListadeReproduccion;
    }

    /**
     * @return the Cancion_IdCancion
     */
    public String getCancion_IdCancion() {
        return Cancion_IdCancion;
    }

    /**
     * @param Cancion_IdCancion the Cancion_IdCancion to set
     */
    public void setCancion_IdCancion(String Cancion_IdCancion) {
        this.Cancion_IdCancion = Cancion_IdCancion;
    }

    /**
     * @return the ListadeReproduccion_idListadeReproduccion
     */
    public String getListadeReproduccion_idListadeReproduccion() {
        return ListadeReproduccion_idListadeReproduccion;
    }

    /**
     * @param ListadeReproduccion_idListadeReproduccion the ListadeReproduccion_idListadeReproduccion to set
     */
    public void setListadeReproduccion_idListadeReproduccion(String ListadeReproduccion_idListadeReproduccion) {
        this.ListadeReproduccion_idListadeReproduccion = ListadeReproduccion_idListadeReproduccion;
    }

}
