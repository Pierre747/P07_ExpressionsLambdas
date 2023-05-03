/*
 * Les expressions lambdas sont arrivées avec la version 8.
 * 
 * Une expression lambda est un court bloc d'instruction qui peut exiger des paramètres et peut
 * retourner une valeur. On peut la nommer comme une fonction anonyme et peut être définie dans le corps d'une méthode.
 * 
 */


package fr.expressionslambdas;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;



// DEFINITION : C'est une une interface qui ne contient qu'une seule méthode.
	// Elle peut avoir n'importe quel nombre de méthodes par défaut (un corps).

// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- //
	// Définition de quelques interfaces fonctionnelles

	interface IMessage
	{
		void afficherMessage();
	}
	
	interface IConversion
	{
		float convertir(float t);
	}
	
	interface IOperation
	{
		float calculer(float x, float y);
	}
	
	// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- //

public class MonApplication
{
	public static void main(String[] args)
	{
		//----------------------->
		
		
		// 1 - Expression lambda sans paramètre et avec une seule instruction dans le corps de la méthode.
		
		// Je veux utiliser cette expresion lambda pour créer un objet qui implémente l'interface
		// fonctionnelle. J'utilise un raccourci syntaxique qui permet de ne pas créer explicitement 
		// la classe qui implémente cette interface. Donc la classe sera crée et instanciée implicitement.

		IMessage message = () -> System.out.println("Etude des expressions lambda");
		System.out.println(message.getClass().getName());
		
		message.afficherMessage();
		
		
		//----------------------->
		
		
		// 2 - Expression lambda sans paramètre avec plusieurs instructions dans le corps de la méthode.
		
		String nom = "Diane";
		
		IMessage message2 = () -> {
			System.out.println("\nBienvenue à Poitiers");
			System.out.println("Votre guide est " + nom);
			};
			
		message2.afficherMessage();
		
		
		//----------------------->
		
		// 3 - Expression lambda a un seul argument
		
		// Tips : dans ce cas les parenthèses ne sont plus obligatoires.
		// S'il n'y a qu'une seule instruction dans le corps, on n'a pas besoin de return
		// On peut l'écrire avec le retour implicite : IConversion celsiusVersFarenheit = t -> t * 9/5 + 32;
		
		IConversion celsiusVersFarenheit = t -> { 
			return t * 9/5 + 32; 
			};
		
		float tf = celsiusVersFarenheit.convertir(20);
		
		System.out.println("\n20 degrés celsius donnent " + tf + " degrés Farenheit");
		
		
		//----------------------->
		
		
		// Créer une instance capable de convertir une tempéreature de Farenheit à Celsius
		
		IConversion farenheitVersCelsius = t -> { 
			return (t - 32) * 5/9; 
			};
			
		float tf2 = farenheitVersCelsius.convertir(68);
		
		System.out.println("\n68 degrés Farenheit donnent " + tf2 + " degrés celsius");
		
		
		//----------------------->
		
		
		// 4.a - Fonction lambda avec 2 paramètres et retour implicite (pas de return)
		
		IOperation operation = (float n, float m) -> n + m;
		
		float result = operation.calculer(3, 5);
		
		System.out.println("\nLe résultat de l'opération est : " + (int)result); // Je caste le result en int pour virer la partie décimale
		
		
		
		//----------------------->
		
		
		// 4.b - Fonction lambda avec 2 paramètres et retour explicite
		
		IOperation operation2 = (float n, float m) -> {return n * m;};
		
		float result2 = operation2.calculer(3, 5);
		
		System.out.println("\nLe résultat de l'opération est : " + (int)result2);
		
		
		//----------------------->
		
		
		// 5 - Passer une expression lambda à une méthode
		
		// Si je ne mets pas la méthode executerCalcul (après mon main) en static, 
		// je peux instancier la méthode en passant par la classe parente :
		
		MonApplication ma = new MonApplication();
		float prixHT = 100;
		float coefTVA = 0.2f;
		float prixTTC = prixHT * ma.executerCalcul(operation2, prixHT, coefTVA);
		System.out.println("\nPrix TTC : " + (int)prixTTC + " euros");
		
		// Beaucoup de méthodes définies par les bibliothèques Java exigent des expressions lambdas.
		// EXEMPLE : les méthodes sort() et forEach() de l'interface List
		
		// Créer une liste à partir d'un aggrégat de valeurs
		List<Integer> listeEntiers = Arrays.asList(255, 700, 25, 100); // Importer : import java.util.List;
 		
		// Je veux trier cette liste du plus petit au plus grand
		listeEntiers.sort( (a, b) -> {
			if(a > b)
				return 1;
			else if(a < b)
				return -1;
			else
				return 0;
		}); // On utilise .sort()
		
		// On va imprimer les éléments de la liste avec un for étendu
		
//		for(int i : listeEntiers)
//			System.out.println(i);
		
		// On va imprimer les éléments de la liste avec un forEach
		
//		listeEntiers.forEach(item -> System.out.println(item));
		
		// Je peux faire la même chose en passant la référence de méthode
		
		listeEntiers.forEach(System.out::println);
		
		// TODO : lire la liste du plus grand au plus petit
		
		// Tester la méthode pourChaque de MonConteneur
		
				MonConteneur<String> containerChaines = new MonConteneur<>(Arrays.asList("Cécile", "Marie", "Julie"));
				containerChaines.pourChaque(System.out::println);
				containerChaines.pourChaque(element -> System.out.println(element));
				
				MonConteneur<Integer> containerEntier= new MonConteneur<>(Arrays.asList(1, 2, 3));
				containerEntier.pourChaque(System.out::println);
				containerEntier.pourChaque(element -> System.out.println(element));
				
				
				// Essayons defaire la somme de toutes les valeurs entières stockées dans le container
				// et accessibles à la méthode pourChaque :
				
				// int somme = 0;
				// containerEntier.pourChaque(element -> somme += element);
				
				// On remarque que le compilateur exige que la variable capturée de l'environnement soit déclarée final
				// donc, constant. Donc l'environnement peut être lu mais pas modifié. L'expression lambda qui capture l'environnement peut
				// le lire mais pas le modifier.
				
				// Ce qui peut être modifié, c'est le contenu d'un objet. On peut donc modifier des valeurs référencées stockées dans le heap(le tas).
				// Pour que cela fonctionne, il suffit de définir un tableau.
				
				int[] tb = { 0 };
				
				containerEntier.pourChaque(element -> tb[0] += element);
				
				System.out.println("Somme des éléments du container : " + tb[0]);
		
		
	} // Fin du Main
	
	
	
	
	
	// Définir une méthode qui a besoin d'une expression lambda passée en paramètres.
	// La méthode suivante reçoit l'expression lambda dans le premier paramètre et
	// les deux valeurs qui participent au calcul dans les paramètres suivants.
	
	private float executerCalcul(IOperation op,float x, float y)
	{
		return op.calculer(x, y);
	}

} // Fin de mon application

//Créer une classe qui définit son propre foreach

class MonConteneur<T>
{
	private List<T> liste;
	
	public MonConteneur(List<T> liste)
	{
		this.liste = liste;
	}
	
	void pourChaque(Consumer<? super T> action)
	{
		for(int i = 0; i < liste.size(); i++)
		{
			action.accept( (T) liste.get(i));
		}
	}
}
