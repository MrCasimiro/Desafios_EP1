import java.util.Scanner;


public class Main {
	
	static int min;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); 
		
		int num_case = 1;
		while(sc.hasNext()) {
			min = Integer.MAX_VALUE;
			
			int R = sc.nextInt();
			int C = sc.nextInt();
			int r1 = sc.nextInt() -1; // Ajustes de parametro pois o a matriz começa do 0.
			int c1 = sc.nextInt() -1;
			int r2 = 2*sc.nextInt() -2;
			int c2 = 2*sc.nextInt() -2;
			
			if(R == 0 && C == 0 && r1 == -1 && r2 == -2 && c1 == -1 && c2 == -2) {
				sc.close();
				return;
			}
			
			String aux = sc.nextLine();
			
			int[][] city = new int[(2*R)-1][(2*C)-1];
			int n = 2*R -1;
			
			if(aux.equals("")) { // caso normal é passado uma matriz nas proximas linhas
				//System.out.println("TESTE");
				
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
				sc.nextLine();
				
			} else { // o resto da matriz está no aux
				//System.out.println(aux);
				String[] valores = aux.split("  ");
				
				boolean point = true; //quando a primeira posição da linha na matriz for ponto ou rua
				int k = 1;
				for(int j = 0; j < n; j++) {
					int i = 0;
					
					while(i < (2*C)-1) {
						if(point) {
							city[j][i] = -1; // interseção ou um ponto
							if(i == 2*C-2) {
								break;
							}
							city[j][++i] = Integer.parseInt(valores[k]);
							k++;
						} else {
							city[j][i] = Integer.parseInt(valores[k]);
							k++;
							if(i == 2*C-2) {
								break;
							}
							city[j][++i] = -1;
						}
						
						i++;
						
					}
					
					point = !point;
				}
				
			}
			
			
			
			//printM(city);
			//System.out.println("\n");
			int teste = Integer.MAX_VALUE;
			int[] m = percorre(city, r1, c1, r2, c2, 0, ' ', false);
			String resp = "";
			
			if(m[0] == teste) {
				resp = "Impossible";
			} else {
				resp = "" + m[0];
			}
			
			System.out.println("Case " + num_case + ": " + resp);
			//System.out.println("\n\n" + m[0]);
			num_case++;
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
	
	public static int[] percorre(int[][] city, int x, int y, int x_end, int y_end, int rua, char direction, boolean mudou) {
		
		if(x == x_end && y == y_end) {
			int[] result = new int[2];
			
			result[0] = rua * 2;
			result[1] = 1;
			//System.out.println("x: " + x + " y: " + y + " tempo rua: " + result[0]);
			
			return result;
		}
		
		int[] norte = inicializa();
		int[] sul = inicializa();
		int[] leste = inicializa();
		int[] oeste = inicializa();
		
		if(x - 1 > 0 && city[x-1][y] > 0) {
			// verifico se tem uma rua e se ela tem valor > 0, se sim vai ter um ponto. - Norte
			boolean aux_mudou = false;
			
			if(direction != 'N') {
				norte[1] = 2;
				aux_mudou = true;
			}
			
			
			int aux = city[x-1][y]; // Vou zerar a rua para não dar stackoverflow
			
			
			city[x-1][y] = 0;
			
			int[] caminho = percorre(city, x-2, y, x_end, y_end, aux, 'N', aux_mudou);
			
			if(mudou) {
				caminho[1] = 2;
			}
			
			city[x-1][y] = aux;
			
			norte[0] = (rua *caminho[1]) + caminho[0];
		}
		
		if(x + 1 < city.length && city[x+1][y] > 0) {
			// - Sul
			boolean aux_mudou = false;
			
			if(direction != 'S') {
				sul[1] = 2;
				aux_mudou = true;
			}
			
			int aux = city[x+1][y];
			
			
			
			city[x+1][y] = 0;
			
			int[] caminho = percorre(city, x+2, y, x_end, y_end, aux, 'S', aux_mudou);
			
			if(mudou) {
				caminho[1] = 2;
			}
			
			city[x+1][y] = aux;
			
			sul[0] = (rua * caminho[1]) + caminho[0];
		}
		
		if(y + 1 < city[0].length && city[x][y+1] > 0) {
			// - Leste
			boolean aux_mudou = false;
			
			if(direction != 'L') {
				leste[1] = 2;
				aux_mudou = true;
			}
			
			int aux = city[x][y+1];
			
			
			
			city[x][y+1] = 0;
			
			int[] caminho = percorre(city, x, y+2, x_end, y_end, aux, 'L', aux_mudou);
			
			if(mudou) {
				caminho[1] = 2;
			}
			
			city[x][y+1] = aux;
			
			leste[0] = (rua * caminho[1]) + caminho[0];
		}
		
		if(y - 1 > 0 && city[x][y-1] > 0) {
			// - Oeste
			boolean aux_mudou = false;
			
			if(direction != 'O') {
				oeste[1] = 2;
				aux_mudou = true;
			}
			
			int aux = city[x][y+1];
			
			
			
			city[x][y+1] = 0;
			
			int[] caminho = percorre(city, x, y-2, x_end, y_end, aux, 'O', aux_mudou);
			
			if(mudou) {
				caminho[1] = 2;
			}
			
			city[x][y-1] = aux;
			
			oeste[0] = (rua * caminho[1]) + caminho[0];
		}
		
		int[] result = verificaMenor(norte, sul, leste, oeste);
		//System.out.println("x: " + x + " y: " + y + " tempo rua: " + result[0]);
		
		return result;
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
}