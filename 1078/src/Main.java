import java.util.Scanner;


public class Main {
	
	static int min;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); 
		
		
		while(sc.hasNext()) {
			min = Integer.MAX_VALUE;
			
			int R = sc.nextInt();
			int C = sc.nextInt();
			int r1 = sc.nextInt() -1; // Ajustes de parametro pois o a matriz começa do 0.
			int c1 = sc.nextInt() -1;
			int r2 = 2*sc.nextInt() -2;
			int c2 = 2*sc.nextInt() -2;
			sc.nextLine();
			
			if(R == 0 && C == 0 && r1 == 0 && r2 == 0 && c1 == 0 && c2 == 0) {
				sc.close();
				return;
			}
			
			int[][] city = new int[(2*R)-1][(2*C)-1];
			int n = 2*R -1;
			boolean point = true; //quando a primeira posição da linha na matriz for ponto ou rua
			
			for(int j = 0; j < n; j++) {
				int i = 0;
				
				while(i < (2*C)-1) {
					if(point) {
						city[j][i] = -1; // interseção ou um ponto
						if(i == 2*C-2) {
							break;
						}
						city[j][++i] = sc.nextInt();
					} else {
						city[j][i] = sc.nextInt();
						if(i == 2*C-2) {
							break;
						}
						city[j][++i] = -1;
					}
					
					i++;
					
				}
				
				point = !point;
			}
			printM(city);
			System.out.println("\n");
			int[] m = percorre(city, r1, c1, r2, c2, 0, ' ');
			
			System.out.println("\n\n" + m[0]);
		}
	}
	
	public static int[] inicializa() {
		int[] result = new int[2];
		result[0] = Integer.MAX_VALUE;
		result[1] = 1;
		
		return result;
	}
	
	public static void printM(int[][] matrix) {
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	// Retorna um vetor, nesse vetor o resultado em si está na primeira posição, na segunda posição está
	// um valor 1 ou 2 para multiplicar pela rua do método retornado
	
	public static int[] percorre(int[][] city, int x, int y, int x_end, int y_end, int rua, char direction) {
		System.out.println("x: " + x + " y: " + y + " tempo rua: " + rua);
		
		if(x == x_end && y == y_end) {
			int[] result = new int[2];
			
			result[0] = rua * 2;
			result[1] = 1;
			System.out.println(result[0]);
			
			return result;
		}
		
		int[] norte = inicializa();
		int[] sul = inicializa();
		int[] leste = inicializa();
		int[] oeste = inicializa();
		
		if(x - 1 > 0 && city[x-1][y] > 0) {
			// verifico se tem uma rua e se ela tem valor > 0, se sim vai ter um ponto. - Norte
			
			if(direction != 'N') {
				norte[1] = 2;
			}
			
			int aux = city[x-1][y]; // Vou zerar a rua para não dar stackoverflow
			
			if(direction == ' ') {
				aux = aux * 2; // Essa é a condição do ínicio, se esse if for executa será um única vez para cada possibilidade de saida do inicio
			}
			city[x-1][y] = 0;
			
			int[] caminho = percorre(city, x-2, y, x_end, y_end, aux, 'N');
			city[x-1][y] = aux;
			
			norte[0] = (rua *caminho[1]) + caminho[0];
		}
		
		if(x + 1 < city.length && city[x+1][y] > 0) {
			// - Sul
			
			if(direction != 'S') {
				sul[1] = 2;
			}
			
			int aux = city[x+1][y];
			
			if(direction == ' ') {
				aux = aux * 2; // Essa é a condição do ínicio, se esse if for executa será um única vez para cada possibilidade de saida do inicio
			}
			
			city[x+1][y] = 0;
			
			int[] caminho = percorre(city, x+2, y, x_end, y_end, aux, 'S');
			city[x+1][y] = aux;
			
			sul[0] = (rua * caminho[1]) + caminho[0];
		}
		
		if(y + 1 < city[0].length && city[x][y+1] > 0) {
			// - Leste
			
			if(direction != 'L') {
				leste[1] = 2;
			}
			
			int aux = city[x][y+1];
			
			if(direction == ' ') {
				aux = aux * 2; // Essa é a condição do ínicio, se esse if for executa será um única vez para cada possibilidade de saida do inicio
			}
			
			city[x][y+1] = 0;
			
			int[] caminho = percorre(city, x, y+2, x_end, y_end, aux, 'L');
			city[x][y+1] = aux;
			
			leste[0] = (rua * caminho[1]) + caminho[0];
		}
		
		if(y - 1 > 0 && city[x][y-1] > 0) {
			// - Oeste
			
			if(direction != 'O') {
				oeste[1] = 2;
			}
			
			int aux = city[x][y+1];
			
			if(direction == ' ') {
				aux = aux * 2; // Essa é a condição do ínicio, se esse if for executa será um única vez para cada possibilidade de saida do inicio
			}
			
			city[x][y+1] = 0;
			
			int[] caminho = percorre(city, x, y-2, x_end, y_end, aux, 'O');
			city[x][y-1] = aux;
			
			oeste[0] = (rua * caminho[1]) + caminho[0];
		}
		
		return verificaMenor(norte, sul, leste, oeste);
	}
	
	public static int[] verificaMenor(int[] norte, int[] sul, int[] leste, int[] oeste) {
		
		if(norte[0] < sul[0] && norte[0] < leste[0] && norte[0] < oeste[0]) {
			return norte;
		} else if(sul[0] < norte[0] && sul[0] < leste[0] && sul[0] < oeste[0]) {
			return sul;
		} else if(leste[0] < norte[0] && leste[0] < sul[0] && leste[0] < oeste[0]) {
			return leste;
		} else {
			return oeste;
		}
		
	}
	
	//o boolean é pra dobrar a velocidade depois de uma mudança de direção
	
	/*
	public static int percorre(int[][] city, int x, int y, int x_end, int y_end, int aresta, char direction, boolean mudou) {
		System.out.println("x: " + x + " y: " + y + " total: " +aresta);
		if(x == x_end && y == y_end) {
			
			System.out.println(aresta*2);
			return aresta*2;
		}
		
		int norte = Integer.MAX_VALUE;
		int sul = Integer.MAX_VALUE;
		int leste = Integer.MAX_VALUE;
		int oeste = Integer.MAX_VALUE;
		
		
		 //Ordem das direções: Norte - Sul - Leste - Oeste
		 
		
		if(x-1 > 0 && city[x-1][y] != 0) {
			int time = aresta;
			boolean aux_m = false;
			
			
			 //FAZER MUDANÇA NESSE MÉTODO PARA QUE ELE FAÇA O CALCULO DE MUDANÇA APÓS PASSAR PELA ARESTA
			 
			
			
			
			
		}
		
		if(x+1 < city.length && city[x+1][y] != 0) {
			int time = total;
			boolean aux_m = false;
			
			if(direction == 'S') {
				time = time + city[x+1][y];
			} else if(direction != 'S' || (x+2 == x_end && y == y_end) || mudou) { // Se ele for chegar ao fim o tempo será dobrado tb
				time = time + 2*city[x+1][y];
				if(direction != 'S') 
					aux_m = true;
			}
			
			int aux = city[x+1][y];
			city[x+1][y] = 0;
			percorre(city, x+2, y, x_end, y_end, time, 'S', aux_m);
			city[x+1][y] = aux;
		}
		
		if(y+1 < city[0].length && city[x][y+1] != 0) {
			int time = total;
			boolean aux_m = false;
			
			if(direction == 'L') {
				time = time + city[x][y+1];
			} else if(direction != 'L' || (x == x_end && y+2 == y_end) || mudou) { // Se ele for chegar ao fim o tempo será dobrado tb
				time = time + 2*city[x][y+1];
				if(direction != 'L')
					aux_m = true;
			}
			
			int aux = city[x][y+1];
			city[x][y+1] = 0;
			percorre(city, x, y+2, x_end, y_end, time, 'L', aux_m);
			city[x][y+1] = aux;
		}
		
		if(y-1 > 0 && city[x][y-1] != 0) {
			int time = total;
			boolean aux_m = false;
			
			if(direction == 'O') {
				time = time + city[x][y-1];
			} else if(direction != 'O' || (x == x_end && y-2 == y_end) || mudou) { // Se ele for chegar ao fim o tempo será dobrado tb
				time = time + 2*city[x][y-2];
				if(direction != 'O')
					aux_m = true;
			}
			
			int aux = city[x][y-1];
			city[x][y-1] = 0;
			percorre(city, x, y-2, x_end, y_end, time, 'O', aux_m);
			city[x][y-1] = aux;
		}
	} 
	*/
}
