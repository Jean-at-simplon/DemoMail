# DemoMail

Demonstration d'envoi de mail en utilisant l'API Javamail et le template Freemarker.

![alt tag](https://user-images.githubusercontent.com/24315341/27771990-66398e02-5f5a-11e7-834a-358228e1fe62.png)

## Pour commencer

Il faut inserer dans le pom :
- Javamail
- Activation
- Freemarker

Ces jar sont à chercher dans les dépots Maven. Pour Freemarker, il faut prendre la derniere version 2.3.26

Dans "resources", faire un fichier .properties pour y mettre les parametres de connexion du mail, en respectant bien les noms des proprietes de javamail, par exemple : 
- mail.smtp.host=smtp.gmail.com
- mail.smtp.socketFactory.port=587.

Documentation des properties de javamail : http://connector.sourceforge.net/doc-files/Properties.html

Dans 'resources/template", faites un template avec l'extension ftlh (freemarker). Dans notre cas, il s'agira d'un fichier html, sans "head" ni "body", les lignes de commandes étant insérer dans une "div". On peut externaliser le css (ce que je n'ai pas fait), externaliser des parties du code qui seront communes aux templates et les insérer avec la balise <#include path>. Pour des champs modifiables, il faut mettre les variables comme ceci : ${maVariable}. Il existe plein d'autres commandes, pour plus de renseignements : http://freemarker.org/

En ce qui concerne les images inline, j'ai choisis de mettre des liens internet, freemarker ayant apparement du mal à gerer les images embarquées (ou moi peut être). Par contre, il les gèrent trés bien en ce qui concerne le css.

 ## Le Java
 
 Le contrôleur n'est là que pour faire la demonstration, on peut l'intégrer dans une classe "service".
 
 La classe gerant l'envoi de mail est de type "service" (@service).
 
 Il faut commencer par instancier un objet "properties" : Properties properties = new Properties();
 
 Après, on récupère les propriétés du fichier "properties" : 
 
 ```java
 try {
			properties.load(new FileInputStream(new File("src/main/resources/javamail.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

```
Cette méthode permet de récupérer automatiquement les propriétés de javamail, à condition que les clés soient exactes, sans être obligé d'instancier chaque propriété.

On ouvre une session avec le mail de l'expéditeur et son mot de passe, que l'on peut prendre depuis le fichier "properties" :

```java
String mail = properties.getProperty("mail.smtp.from");
		String passe = properties.getProperty("mail.smtp.password");
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mail, passe);
			}
		});

```

On prépare le message avec l'expéditeur, le destinataire et le sujet. Puis, on instancie le corps du message.

Là commence la configuration du template.
La première ligne permet de determiner quelle configuration de Freemarker on va utiliser, car chaque version est diffèrente d'une autre. La seconde ligne indique ou trouver le template, ensuite, quel type d'encodage on utilise. Les deux dernieres ne sont pas obligatoires, mais utilent pour trouver d'ou viennent les problèmes.
```java
Configuration configuration = new Configuration(Configuration.VERSION_2_3_26);
configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
configuration.setDefaultEncoding("UTF-8");
configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
configuration.setLogTemplateExceptions(false);

```
Puis, on instancie le template et on lui indique son nom. 
Les données envoyées au template sont sous la forme d'une hashmap<key,value>, la key étant le nom de la variable du template que l'on souhaite écrire. Puis on instancie un String avec la méthode "writer" qui permet d'écrire les string en sortie et on procède à l'écriture des données.
```java
	Template template = configuration.getTemplate("email"+statut+".ftlh");
						
	Map<String, String> rootMap = new HashMap<String, String>();
	rootMap.put("statut", statut);
	rootMap.put("nom", "LEFRANCOIS");
	rootMap.put("prenom", "Jean");
	rootMap.put("typeConge", "Congé payé");
	rootMap.put("debutConge", "21/07/2017");
	rootMap.put("finConge", "01/01/2028");
	rootMap.put("numDemande", "JEAN00001");
	Writer out = new StringWriter();
	template.process(rootMap, out);
 ```
 On ajoute le template dans le corps du message.
 On instancie un objet "multipart" pour signifier que le message peut avoir plusieurs types mime, "related" lui fait comprendre que des images seront insérer dans le mail. On y ajoute le corps du message que l'on met dans l'objet message. Puis on envoie le tout avec la méthode ```java Transport.send(message)```
 
 Et le message est parti.
 
 N'hésitez pas à utiliser et partager le code.
    
		
