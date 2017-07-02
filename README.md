# DemoMail

Demonstration d'envoi de mail en utilisant l'API Javamail et le template Freemarker.

![alt tag](https://user-images.githubusercontent.com/24315341/27771990-66398e02-5f5a-11e7-834a-358228e1fe62.png)

## Pour commencer

Il faut inserer dans le pom :
- Javamail
- Activation
- Freemarker

Dans "resources", faire un fichier .properties pour y mettre les parametres de connexion du mail, en respectant bien les noms des proprietes de javamail, par exemple : 
- mail.smtp.host=smtp.gmail.com
- mail.smtp.socketFactory.port=587.

Documentation des properties de javamail : http://connector.sourceforge.net/doc-files/Properties.html

Puis faire un template avec l'extension ftlh (freemarker). Dans notre cas, il s'agira d'un fichier html, sans <head> ni <body>, les lignes de commandes étant insérer dans une <div>. On peut externaliser le css (ce que je n'ai pas fait), externaliser des parties du code qui seront communes aux templates et les insérer avec la balise <#include path>. Pour des champs modifiables, il faut mettre les variables comme ceci : ${maVariable}. Il existe plein d'autres commandes, pour plus de renseignements : http://freemarker.org/

En ce qui concerne les images inline, j'ai choisis de mettre des liens internet, freemarker ayant apparement du mal à gerer les images embarquées (ou moi peut être). Par contre, il les gèrent trés bien en ce qui concerne le css.
