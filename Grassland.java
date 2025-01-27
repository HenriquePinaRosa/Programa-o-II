import java.util.ArrayList;
import java.util.Collections;

public class Grassland {

  public final static int EMPTY = 0;
  public final static int CARROT = 1;
  public final static int RABBIT = 2;
  
  private int height = 0;
  private int width = 0;
  private int starveTime = 0;
  private Object[][] meadow;
  
  public Grassland(int i, int j, int starveTime){ //construtor

    this.width = i;
    this.height = j;
    this.starveTime = starveTime;
    meadow = new Object[width][height];

    for (int aux = 0; aux < height; aux++) //inicializa o Array de objetos com 0 (ou seja, o prado está vazio)
     for (int auy = 0; auy < width; auy++)
      meadow[aux][auy] = 0;
  }
  
  public int width() {
    return this.width;
  }

  public int height() {
    return this.height;
  }

  public int starveTime() {
    return this.starveTime;
  }

  public void addCarrot(int x, int y) { //////////////-> modf try and catch

    try{
      if(this.meadow[x][y] instanceof Integer ){
        if(this.meadow[x][y].equals(EMPTY)){
        this.meadow[x][y] = CARROT;
        }
      }    //in other cases is not altered
      else if(this.meadow[x][y] == null){
        throw new NullPointerException("add carrot -> object is null"); //use dif kind of exception
      }
    }
    catch(NullPointerException A){
      System.out.println(A.getMessage());
    }
  }

  public void addRabbit(int x, int y, RABBIT r) {

    try{
      if(this.meadow[x][y] instanceof Integer){
        if(this.meadow[x][y].equals(EMPTY)){
        this.meadow[x][y] = r;
        }
        
      }
      else if(this.meadow[x][y] == null){
        throw new NullPointerException("add rabbit -> object is null"); // ig we gotta change this exception type
      }
    }
    catch(NullPointerException W){
      System.out.println(W.getMessage());
    }

  }

  public int cellContents(int x, int y){ //* watch out the exceptions */

    try{
      if(this.meadow[x][y] instanceof Integer){
        if(this.meadow[x][y].equals(EMPTY)){
          return EMPTY;
        }
        else if(this.meadow[x][y].equals(CARROT)){
          return CARROT;
        }
      }
      else if(this.meadow[x][y] instanceof RABBIT){
        return RABBIT;
      }
      else{
        throw new IllegalArgumentException("cell contents: number != than empty, carrot or rabbit");
      }
    }
    catch (IllegalArgumentException E){
      System.out.println(E.getMessage());
    }
    return -1;
  }

  /* esse método calcula as coordenadas adjecentes do ponto i, j. Devolve todas num array list */

  private ArrayList<int[]> coor_adjacentes(int i, int j){ 

    ArrayList<int[]> coordinates = new ArrayList<int[]>();
    int aux_i = Math.floorMod(i - 1, width);
    int aux_j = Math.floorMod(j- 1, height);
    int aux_a = Math.floorMod(i + 1, width);
    int aux_b = Math.floorMod(j + 1, width);

    int topLeft[] = {aux_i, aux_j}; 
    int top[] = {i, aux_j};
    int topRight[] = {aux_a, aux_j};

    int midLeft[] = {aux_i, j};
    int midRight[] = {aux_a, j};

    int botLeft[] = {aux_i, aux_b};
    int bot[] = {i, aux_b};
    int botRight[] = {aux_a, aux_b};

    coordinates.add(topLeft);
    coordinates.add(top);
    coordinates.add(topRight);
    coordinates.add(midLeft);
    coordinates.add(midRight);
    coordinates.add(botLeft);
    coordinates.add(bot);
    coordinates.add(botRight);

    return coordinates;
  }

  /* este método verifica o conteúdo dos pontos adjacentes da matriz meadow. Coloca o conteúdo (0, 1 ou 2) num ArrayList*/

  private ArrayList<Integer> items(int i, int j){

    ArrayList<int[]> coordenadas = coor_adjacentes(i, j);
    ArrayList<Integer> conteudo = new ArrayList<Integer>();
    int aux;

    for(int[] index: coordenadas){
      aux = cellContents(index[0], index[1]);
      conteudo.add(aux);
    }

    return conteudo;
  }

  /*
   * Este método escolhe um ponto dentre todos os adjacentes de i, j que contenha "type"
   * Por exemplo, escolhe um ponto adjacente a (0,0) que contenha um coelho
   * Retorna o ponto (x,y) num array
  */

  private int[] choose(int i, int j, int type){
    
    ArrayList<int[]> coordenadas = coor_adjacentes(i, j);
    ArrayList<Integer> conteudo = items(i, j);
    int index = conteudo.indexOf(type); //no caso, escolhe a 1ª cell em que aparece a cenoura

    return coordenadas.get(index);
  }

  public Grassland timeStep(){

    Grassland meowtwo = new Grassland(width, height, starveTime);
    int ponto[] = new int[2];
    int age = 0;

    try{
      for(int j = 0; j < this.height; j++){
        for(int i = 0; i < this.width; i++){

          ArrayList<Integer> coordinates = items(i, j);
          int amount_rabbits = Collections.frequency(coordinates, RABBIT);
          int amount_carrots = Collections.frequency(coordinates, CARROT);

          // CASO 1 -> A CELL TEM UM COELHO //
          if(this.cellContents(i, j) == RABBIT){
  
            if( ((RABBIT) meadow[i][j]).getAge() > starveTime){ // ou seja, se o coelho tem starvetime + 1 unidades de tempo sem comida
              meowtwo.meadow[i][j] = EMPTY;
            }
            else{
              meowtwo.addRabbit(i, j, new RABBIT());

              if(amount_carrots > 0){                          //se algum vizinho é cenoura
                ponto = choose(i, j, CARROT);
                meowtwo.meadow[ponto[0]][ponto[1]] = EMPTY;
              }
              else{                                            //nenhum vizinho é cenoura 
                age = ((RABBIT) this.meadow[i][j]).getAge();
                ((RABBIT) meowtwo.meadow[i][j]).setAge(age);
              }

            }
          }
  
          // CASO 2 -> A CELL TEM UMA CENOURA //
          else if(this.cellContents(i, j) == CARROT){
  
            if(amount_rabbits == 1){                            // tiver 1 coelho vizinho
              ponto = choose(i, j, RABBIT);
              meowtwo.addRabbit(ponto[0], ponto[1], new RABBIT());
              meowtwo.meadow[i][j] = EMPTY;
            }
            else if(amount_rabbits >= 2){                       // tiver 2 ou mais coelhos vizinhos
              meowtwo.addRabbit(i, j, new RABBIT());
            }
            else{                                              // se não existirem coelhos vizinhos, mantem-se
              meowtwo.addCarrot(i, j);
            }
          }
  
          // CASO 3 -> A CELL É RELVA/ ESTÁ VAZIA //
          else if(this.cellContents(i, j) == EMPTY){
  
            if(amount_carrots >= 2 && amount_rabbits <= 1){    // duas ou mais cenouras vizinhas; 1 ou menos coelhos vizinhos
              meowtwo.addCarrot(i, j);
            }
            else if(amount_carrots >= 2 && amount_rabbits >= 2){ // duas cenouras ou mais cenoras vizinhas; 2 ou mais coelhos vizinhos
              meowtwo.addRabbit(i, j, new RABBIT());
            }
            // se não for nenhum dos casos, ela se mantem vazia 
          }
          else{          // SE NÃO FOR CENOURA, COELHO OU EMPTY, THROW EXCECPTION
            throw new IllegalArgumentException("something went wrong");
          }
  
        }
      }
    }
    catch(IllegalArgumentException f){
      System.out.println(f.getMessage());
    }

    return meowtwo;
  }
}

