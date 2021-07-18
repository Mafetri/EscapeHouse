package estructuras.arbol;

import estructuras.lista.Lista;
import estructuras.datos.Desafio;

public class DiccionarioAVL {
    private NodoAVLDicc raiz;

    public DiccionarioAVL() {
        this.raiz = null;
    }

    // ---- Insertar ----
    public boolean insertar(Comparable clave, Object dato) {
        boolean exito = false;

        // Si el arbol esta vacio coloco clave en su raiz
        if (this.raiz == null) {
            this.raiz = new NodoAVLDicc(clave, dato, null, null);
        } else {
            // Sino lo agrego en la posicion correspondiente
            exito = insertarAux(this.raiz, clave, dato, null);
        }

        return exito;
    }

    private boolean insertarAux(NodoAVLDicc nodo, Comparable elem, Object dato, NodoAVLDicc padre) {
        boolean exito = true;

        // Si el elemento del nodo actual es igual a elem
        if (elem.compareTo(nodo.getClave()) == 0) {
            // La insersion es falsa ya que no puede haber elementos repetidos
            exito = false;
        } else if (elem.compareTo(nodo.getClave()) < 0) {
            // Si elem es mas chico que el elemento del nodo
            // Y si el nodo tiene hijo izquierdo, baja por ese lado
            if (nodo.getIzquierdo() != null) {
                exito = insertarAux(nodo.getIzquierdo(), elem, dato, nodo);
            } else {
                // Sino agrega el elemento
                nodo.setIzquierdo(new NodoAVLDicc(elem, dato, null, null));
            }
        } else {
            // Si el elemento entonces es mas grande
            // Y si tiene hijo derecho
            if (nodo.getDerecho() != null) {
                // Bajo por derecha
                exito = insertarAux(nodo.getDerecho(), elem, dato, nodo);
            } else {
                // Sino lo arego a la derecha
                nodo.setDerecho(new NodoAVLDicc(elem, dato, null, null));
            }
        }

        // Recalculo la altura del nodo
        nodo.recalcularAltura();

        int balance;
        int balanceIzq;
        int balanceDer;

        balance = alturaNodo(nodo.getIzquierdo()) - alturaNodo(nodo.getDerecho());

        // Balance del hijo izquierdo
        if (nodo.getIzquierdo() != null) {
            balanceIzq = alturaNodo(nodo.getIzquierdo().getIzquierdo()) - alturaNodo(nodo.getIzquierdo().getDerecho());
        } else {
            balanceIzq = 0;
        }
        // Balance del hijo derecho
        if (nodo.getDerecho() != null) {
            balanceDer = alturaNodo(nodo.getDerecho().getIzquierdo()) - alturaNodo(nodo.getDerecho().getDerecho());
        } else {
            balanceDer = 0;
        }

        // Rotacion simple derecha (si esta caido a la izquierda y su hijo izquierdo tiene el mismo signo)
        if (balance > 1 && balanceIzq >= 0) {
            if (padre != null) {
                // Si el nodo a rotar es el hijo izquierdo del padre
                if(padre.getIzquierdo().equals(nodo)){
                    padre.setIzquierdo(rotarDerecha(nodo));
                }else{
                    // Si el nodo a rotar es el hijo derecho del padre
                    padre.setDerecho(rotarDerecha(nodo));
                }
            } else {
                this.raiz = rotarDerecha(nodo);
            }
        }

        // Rotacion simple izquierda (si esta caido a la derecha y su hijo derecho tiene el mismo signo)
        if (balance < -1 && balanceDer <= 0) {
            if (padre != null) {
                // Si el nodo a rotar es el hijo izquierdo del padre
                if(padre.getIzquierdo().equals(nodo)){
                    padre.setIzquierdo(rotarIzquierda(nodo));
                }else{
                    // Si el nodo a rotar es el hijo derecho del padre
                    padre.setDerecho(rotarIzquierda(nodo));
                }
            } else {
                this.raiz = rotarIzquierda(nodo);
            }
        }

        // Rotacion izquierda-derecha (si esta caido a la izquierda y su hijo izquiedo tiene distinto signo)
        if(balance > 1 && balanceIzq < 0){
            nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));
            if (padre != null) {
                // Si el nodo a rotar es el hijo izquierdo del padre
                if(padre.getIzquierdo().equals(nodo)){
                    padre.setIzquierdo(rotarDerecha(nodo));
                }else{
                    // Si el nodo a rotar es el hijo derecho del padre
                    padre.setDerecho(rotarDerecha(nodo));
                }
            } else {
                this.raiz = rotarDerecha(nodo);
            }
        }

        // Rotacion derecha-izquierda (si esta caido a la derecha y su hijo derecho tiene distinto signo)
        if(balance < -1 && balanceDer > 0){
            nodo.setDerecho(rotarDerecha(nodo.getDerecho()));
            if (padre != null) {
                // Si el nodo a rotar es el hijo izquierdo del padre
                if(padre.getIzquierdo().equals(nodo)){
                    padre.setIzquierdo(rotarIzquierda(nodo));
                }else{
                    // Si el nodo a rotar es el hijo derecho del padre
                    padre.setDerecho(rotarIzquierda(nodo));
                }
            } else {
                this.raiz = rotarIzquierda(nodo);
            }
        }

        return exito;
    }

    private NodoAVLDicc rotarIzquierda(NodoAVLDicc padre) {
        NodoAVLDicc hijo, temp;

        hijo = padre.getDerecho();
        temp = hijo.getIzquierdo();
        hijo.setIzquierdo(padre);
        padre.setDerecho(temp);

        padre.recalcularAltura();
        hijo.recalcularAltura();

        return hijo;
    }

    private NodoAVLDicc rotarDerecha(NodoAVLDicc padre) {
        NodoAVLDicc hijo, temp;

        hijo = padre.getIzquierdo();
        temp = hijo.getDerecho();
        hijo.setDerecho(padre);
        padre.setIzquierdo(temp);

        padre.recalcularAltura();
        hijo.recalcularAltura();

        return hijo;
    }

    private int alturaNodo(NodoAVLDicc nodo) {
        int alt;
        if (nodo == null) {
            alt = -1;
        } else {
            alt = nodo.getAltura();
        }
        return alt;
    }

    // ---- Pertenece ----
    public boolean pertenece(Comparable elem) {
        boolean exito = false;
        if (this.raiz != null && elem != null) {
            exito = perteneceAux(this.raiz, elem);
        }
        return exito;
    }

    private boolean perteneceAux(NodoAVLDicc nodo, Comparable elem) {
        boolean exito = false;

        // Si nodo no es nulo
        if (nodo != null) {
            // Si el nodo es el elemento que busco, la busqueda fue un exito
            if (elem.compareTo(nodo.getClave()) == 0) {
                exito = true;
            } else {
                // Sino busco por izquierda o por derecha
                if (elem.compareTo(nodo.getClave()) < 0) {
                    exito = perteneceAux(nodo.getIzquierdo(), elem);
                } else {
                    exito = perteneceAux(nodo.getDerecho(), elem);
                }
            }
        }
        return exito;
    }

    // ---- Eliminar ----
    public boolean eliminar(Comparable elem) {
        boolean exito = false;
        if (this.raiz != null && elem != null) {
            exito = eliminarAux(this.raiz, null, elem);
        }
        return exito;
    }

    private boolean eliminarAux(NodoAVLDicc nodo, NodoAVLDicc padre, Comparable elem) {
        boolean exito = false;

        if (nodo != null) {

            if (elem.compareTo(nodo.getClave()) == 0) {
                exito = true;
                // Caso 1: el elemento buscado no tiene hijos
                if (nodo.getDerecho() == null && nodo.getIzquierdo() == null) {
                    eliminarCaso1(padre, elem);
                } else {
                    // Caso 2: el elemento buscado tiene al menos un hijo
                    if (nodo.getDerecho() == null || nodo.getIzquierdo() == null) {
                        eliminarCaso2(nodo, padre, elem);
                    } else {
                        // Caso 3: el elemento buscado tiene ambos hijos
                        eliminarCaso3(nodo);
                    }
                }
            } else {
                // Sino busco por izquierda o por derecha
                if (elem.compareTo(nodo.getClave()) < 0) {
                    exito = eliminarAux(nodo.getIzquierdo(), nodo, elem);
                } else {
                    exito = eliminarAux(nodo.getDerecho(), nodo, elem);
                }
            }
        }

        if (exito) {
            // Recalculo la altura del nodo
            nodo.recalcularAltura();

            int balance;
            int balanceIzq;
            int balanceDer;

            balance = alturaNodo(nodo.getIzquierdo()) - alturaNodo(nodo.getDerecho());

            // Balance del hijo izquierdo
            if (nodo.getIzquierdo() != null) {
                balanceIzq = alturaNodo(nodo.getIzquierdo().getIzquierdo())
                        - alturaNodo(nodo.getIzquierdo().getDerecho());
            } else {
                balanceIzq = 0;
            }
            // Balance del hijo derecho
            if (nodo.getDerecho() != null) {
                balanceDer = alturaNodo(nodo.getDerecho().getIzquierdo()) - alturaNodo(nodo.getDerecho().getDerecho());
            } else {
                balanceDer = 0;
            }

            // Rotacion simple izquierda
            if (balance > 1 && balanceIzq >= 0) {
                if (padre != null) {
                    padre.setIzquierdo(rotarDerecha(nodo));
                } else {
                    this.raiz = rotarDerecha(nodo);
                }
            }

            // Rotacion izquierda-derecha
            if (balance > 1 && balanceIzq < 0) {
                nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));
                if (padre != null) {
                    padre.setIzquierdo(rotarDerecha(nodo));
                } else {
                    this.raiz = rotarDerecha(nodo);
                }
            }

            // Rotacion simple derecha
            if (balance < -1 && balanceDer <= 0) {
                if (padre != null) {
                    padre.setDerecho(rotarIzquierda(nodo));
                } else {
                    this.raiz = rotarIzquierda(nodo);
                }
            }

            // Rotacion derecha-izquierda
            if (balance < -1 && balanceDer > 0) {
                nodo.setDerecho(rotarDerecha(nodo.getDerecho()));
                if (padre != null) {
                    padre.setDerecho(rotarIzquierda(nodo));
                } else {
                    this.raiz = rotarIzquierda(nodo);
                }
            }
        }

        return exito;
    }

    private void eliminarCaso1(NodoAVLDicc nodo, Comparable elem) {
        // Si el elemento es la raiz entonces el nodo (padre) es nulo
        if (nodo == null) {
            this.raiz = null;
        } else {
            // Sino elimino a ese hijo si es que esta a la izquierda o a la derecha
            if (elem.compareTo(nodo.getClave()) < 0) {
                nodo.setIzquierdo(null);
            } else {
                nodo.setDerecho(null);
            }
        }
    }

    private void eliminarCaso2(NodoAVLDicc nodo, NodoAVLDicc padre, Comparable elem) {
        NodoAVLDicc izq = nodo.getIzquierdo();
        NodoAVLDicc der = nodo.getDerecho();

        // Si el elemento buscado es la raiz
        if (padre == null) {
            // Pregunto cual de los dos hijos tiene y el que no sea nulo lo hago raiz
            if (izq != null) {
                this.raiz = izq;
            } else {
                this.raiz = der;
            }
        } else {
            // Si el elemento esta a la izquierda
            if (elem.compareTo(padre.getClave()) < 0) {
                if (izq != null) {
                    padre.setIzquierdo(izq);
                } else {
                    padre.setIzquierdo(der);
                }
            } else {
                // Si esta a la derecha
                if (izq != null) {
                    padre.setDerecho(izq);
                } else {
                    padre.setDerecho(der);
                }
            }
        }
    }

    private void eliminarCaso3(NodoAVLDicc nodo) {
        // Candidato A: El mayor elemento del subárbol izquierdo de N
        NodoAVLDicc nodoCandidato = nodo.getIzquierdo();
        NodoAVLDicc padreCandidato = nodo;

        // Busco el mayor elemento del subárbol izquierdo de N
        while (nodoCandidato.getDerecho() != null) {
            padreCandidato = nodoCandidato;
            nodoCandidato = nodoCandidato.getDerecho();
        }

        nodo.setClave(nodoCandidato.getClave());
        nodo.setDato(nodoCandidato.getDato());

        // Si el hijo izquierdo del nodo actual es igual al candidato (osea que el
        // candidato no tiene hijos derechos)
        if (nodo.getIzquierdo() == nodoCandidato) {
            // Seteo a la izquierda del nodo, lo que tenga el candidato a la izquierda
            nodo.setIzquierdo(nodoCandidato.getIzquierdo());
        } else {
            // Sino, seteo al padre la rama izquierda a la derecha (ya que candidato no va a
            // tener hijo derecho)
            padreCandidato.setDerecho(nodoCandidato.getIzquierdo());
        }
    }

    // ---- Listar (preorden) ----
    public Lista listar() {
        Lista lis = new Lista();

        if (this.raiz != null) {
            listarAux(this.raiz, lis);
        }

        return lis;
    }

    private void listarAux(NodoAVLDicc aux, Lista lis) {
        if (aux != null) {
            // Guardo en la lista el elemento
            lis.insertar(aux.getClave(), lis.longitud() + 1);

            // Visita los hijos
            listarAux(aux.getIzquierdo(), lis);
            listarAux(aux.getDerecho(), lis);
        }

    }

    // ---- Listar Rango ----
    public Lista listarRango(Comparable min, Comparable max) {
        Lista lis = new Lista();

        if (min != null && max != null) {
            listarRangoAux(this.raiz, lis, min, max);
        }

        return lis;
    }

    private void listarRangoAux(NodoAVLDicc nodo, Lista lis, Comparable min, Comparable max) {
        // Mientras el nodo no sea nulo
        if (nodo != null) {
            Comparable elem = nodo.getClave();

            // Si el elemento es mas chico que max hago la llamada recursiva por derecha
            if (elem.compareTo(max) < 0) {
                listarRangoAux(nodo.getDerecho(), lis, min, max);
            }
            // Si esta entre min y max entonces lo guardo en la lista
            if (elem.compareTo(min) >= 0 && elem.compareTo(max) <= 0) {
                lis.insertar(elem, 1);
            }
            // Si es mas grande que min, entonces hago la llamada recursiva por izquierda
            if (elem.compareTo(min) > 0) {
                listarRangoAux(nodo.getIzquierdo(), lis, min, max);
            }
        }
    }

    // ---- Listar Rango Datos ----
    public Lista listarRangoDatos(Comparable min, Comparable max) {
        Lista lis = new Lista();

        if (min != null && max != null && this.raiz != null) {
            listarRangoDatosAux(this.raiz, lis, min, max);
        }

        return lis;
    }

    private void listarRangoDatosAux(NodoAVLDicc nodo, Lista lis, Comparable min, Comparable max) {
        // Mientras el nodo no sea nulo
        if (nodo != null) {
            Comparable elem = nodo.getClave();

            // Si el elemento es mas chico que max hago la llamada recursiva por derecha
            if (elem.compareTo(max) < 0) {
                listarRangoDatosAux(nodo.getDerecho(), lis, min, max);
            }
            // Si esta entre min y max entonces guardo en la lista los datos
            if (elem.compareTo(min) >= 0 && elem.compareTo(max) <= 0) {
                lis.insertar(nodo.getDato(), 1);
            }
            // Si es mas grande que min, entonces hago la llamada recursiva por izquierda
            if (elem.compareTo(min) > 0) {
                listarRangoDatosAux(nodo.getIzquierdo(), lis, min, max);
            }
        }

    }

    // ---- Elemento Minimo ----
    public Comparable minimoElem() {
        Comparable min = null;
        if (this.raiz != null) {
            min = minimoElemAux(this.raiz);
        }
        return min;
    }

    private Comparable minimoElemAux(NodoAVLDicc aux) {
        // Bajo por izquierda hasta que no haya mas nodos
        while (aux.getIzquierdo() != null) {
            aux = aux.getIzquierdo();
        }
        return aux.getClave();
    }

    // ---- Elemento Maximo ----
    public Comparable maximoElem() {
        Comparable min = null;
        if (this.raiz != null) {
            min = maximoElemAux(this.raiz);
        }
        return min;
    }

    private Comparable maximoElemAux(NodoAVLDicc nodo) {
        // Bajo por derecha hasta que no haya mas nodos
        while (nodo.getDerecho() != null) {
            nodo = nodo.getDerecho();
        }
        return nodo.getClave();
    }

    // ---- Es Vacio ----
    public boolean vacio() {
        return this.raiz == null;
    }

    // ---- Vaciar ----
    public void vaciar() {
        this.raiz = null;
    }

    // ---- Clone ----
    public DiccionarioAVL clone() {
        DiccionarioAVL clone = new DiccionarioAVL();

        if (this.raiz != null) {
            clone.raiz = cloneAux(this.raiz);
        }

        return clone;
    }

    private NodoAVLDicc cloneAux(NodoAVLDicc aux) {
        NodoAVLDicc clonado = null;

        // Si el aux no es nulo
        if (aux != null) {
            // Guardo en clonado un nuevo nodo con el elemento actual y sus hijos clonados
            // recursivos
            clonado = new NodoAVLDicc(aux.getClave(), aux.getDato(), cloneAux(aux.getIzquierdo()),
                    cloneAux(aux.getDerecho()));
        }

        return clonado;
    }

    // ---- To String ----
    public String toString() {
        String enTexto = "[ ARBOL VACIO ]";

        if (this.raiz != null) {
            enTexto = toStringAux(this.raiz);
        }

        return enTexto;
    }

    private String toStringAux(NodoAVLDicc aux) {
        String enTexto = "";
        if (aux != null) {
            enTexto += aux.getClave().toString() + "  HI: ";
            if (aux.getIzquierdo() == null) {
                enTexto += " - ";
            } else {
                enTexto += aux.getIzquierdo().getClave().toString();
            }
            enTexto += "  HD: ";
            if (aux.getDerecho() == null) {
                enTexto += " - ";
            } else {
                enTexto += aux.getDerecho().getClave().toString();
            }
            enTexto += "\n";
            enTexto += toStringAux(aux.getIzquierdo());
            enTexto += toStringAux(aux.getDerecho());
        }

        return enTexto;
    }

    // ---- Recuperar Datos ----
    public Object recuperarDatos(Comparable clave) {
        NodoAVLDicc nodo = new NodoAVLDicc(null, null, null, null);
        Object aRetornar = null;
        if (this.raiz != null && clave != null) {
            nodo = recuperarNodoAux(clave, this.raiz);
            if (nodo != null) {
                aRetornar = nodo.getDato();
            }
        }
        return aRetornar;
    }

    private NodoAVLDicc recuperarNodoAux(Comparable clave, NodoAVLDicc nodo) {
        NodoAVLDicc aRetornar = new NodoAVLDicc(null, null, null, null);
        if (nodo != null) {
            // Si el nodo es el elemento que busco, la busqueda fue un exito
            if (clave.compareTo(nodo.getClave()) == 0) {
                aRetornar = nodo;
            } else {
                // Sino busco por izquierda o por derecha
                if (clave.compareTo(nodo.getClave()) < 0) {
                    aRetornar = recuperarNodoAux(clave, nodo.getIzquierdo());
                } else {
                    aRetornar = recuperarNodoAux(clave, nodo.getDerecho());
                }
            }
        }
        return aRetornar;
    }
}
