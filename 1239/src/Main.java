import java.util.Scanner;


public class Main {
	
	static int total = 0; // Variável que vai armazenar o maior tamanho, no momento, da string encontrada
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		sc.nextLine();
		
		while(n > 0) {
			String line = sc.nextLine();
			String[] input = line.split(" ");
			
			int k = Integer.parseInt(input[1]);
			String word = input[0];
			//char[] word = line.toCharArray();
			//do something
			palindrome(word, k, 0, word.length()-1);
			n--;
			
			System.out.println(total);
			total = 0;
		}
	}
	/*
		Método recursivo ele pega a string original e verifica se em k tentativas ela é um palíndromo
		Caso não seja, então chama duas recursões, por exemplo se a string original for "testando" e
		tiver qe chamar recursão, a primeira será para "testand" e a segunda para "estando".
		
		OBS: os parâmetros begin e end são totalmente desnecessários, desconsidere-os. 
	*/
	public static void palindrome(String word, int k, int begin, int end) {
		//System.out.println(word + " " + begin + " " + end);
		if(word.length() < total) return;
		if(word.length() == 1) { // Se a palavra for uma letra ela naturalmente é um palindromo
			if(total == 0) total = 1; 
			return;
		}
		
		int i = 0;
		int n = word.length() -1;
		char[] subs_word = word.toCharArray(); // Usado pra não alterar o original, caso precise analisar as substrings.
		int subs_k = k; //igual ao subs_word
		boolean reduce = false; // boolean usado para verificar se será necessário analisar as substrings.
		
		while(i <= n) {
			if(subs_word[i] != subs_word[n]) {
				if(subs_k > 0) {
					subs_word[i] = subs_word[n]; //não tem necessidade disso, não afetará em nada.
					subs_k--;
				} else {
					reduce = true;
					break;
				}
			}
			
			i++;
			n--;
		}
		
		if(!reduce) {
			total = word.length();
		} else {
			//System.out.println(word.substring(begin, end-1) + "    " + word.substring(begin+1, end));
			palindrome(word.substring(begin, end), k, begin, end-1);
			//if(begin == end -1) return result1;
			palindrome(word.substring(begin+1, end+1), k, begin, end-1);
		}
	}
}
