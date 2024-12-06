# Yoga App

Ce projet est une application composée d'un backend en **Java Spring Boot** et d'un frontend en **Angular**.

---

## Prérequis

Avant de commencer, assurez-vous d'avoir les outils suivants installés sur votre machine :

### **Backend**
- [JDK 11](https://www.oracle.com/java/technologies/javase-downloads.html) ou version compatible
- [Maven](https://maven.apache.org/download.cgi)
- [MariaDB](https://mariadb.org/download/)

### **Frontend**
- [Node.js](https://nodejs.org/) (version recommandée : 16 ou plus récente)
- [npm](https://www.npmjs.com/)

---

## Structure du projet

- `backend/` : contient le code source du backend en Spring Boot.
- `frontend/` : contient le code source du frontend en Angular.

---

## Installation

### 1. Backend

1. Clonez le dépôt et accédez au dossier `backend/` :
   ```bash
   cd backend
   ```

2. Configurez votre base de données MariaDB :
   - Créez une base de données nommée `testProjet_OC`.
   - Configurez l'utilisateur et le mot de passe dans le fichier `application.properties` :
     ```properties
     spring.datasource.url=jdbc:mariadb://<IP_DE_VOTRE_BASE>:3306/testProjet_OC
     spring.datasource.username=<VOTRE_UTILISATEUR>
     spring.datasource.password=<VOTRE_MOT_DE_PASSE>
     ```

3. Compilez et démarrez le serveur :
   - Depuis votre IDE (par exemple Visual Studio Code), cliquez sur **Run** pour démarrer le serveur.
   - Le backend sera accessible sur `http://localhost:8080`.

### 2. Frontend

1. Accédez au dossier `frontend/` :
   ```bash
   cd frontend
   ```

2. Installez les dépendances :
   ```bash
   npm install
   ```

3. Configurez l'URL du backend dans le fichier d'environnement Angular (`src/environments/environment.ts`) :
   ```typescript
   export const environment = {
     production: false,
     apiUrl: 'http://localhost:8080'
   };
   ```

4. Démarrez l'application :
   ```bash
   npm start
   ```
   Par défaut, le frontend sera accessible sur `http://localhost:4200`.

---

## Tests

### Backend

#### Exécuter les tests
1. Depuis votre IDE, ouvrez l'onglet **Maven**.
2. Naviguez vers **Lifecycle** > **test** et exécutez cette commande pour lancer les tests.

#### Couverture de test
- La couverture de test est configurée avec **JaCoCo**. Un rapport est généré dans `target/site/jacoco/index.html` après l'exécution des tests.

### Frontend

#### Tests unitaires
1. Depuis le dossier `frontend/`, exécutez :
   ```bash
   npm test
   ```
   Cela utilise **Jest** pour exécuter les tests unitaires et génère un rapport de couverture dans le dossier `coverage/`.

#### Tests End-to-End (E2E)
1. Démarrez le serveur Angular avec la couverture activée :
   ```bash
   npm run build && npm run e2e:coverage
   ```
2. Lancez les tests E2E avec Cypress :
   ```bash
   npm run cypress:run
   ```
   Pour une interface graphique interactive, utilisez :
   ```bash
   npm run cypress:open
   ```

---

## Déploiement

### Backend
Pour générer un fichier `.jar` exécutable, utilisez la commande suivante depuis le dossier `backend/` :
```bash
mvn package
```
Le fichier `.jar` sera disponible dans le dossier `target/`.

### Frontend
Pour générer une version de production, utilisez :
```bash
ng build --configuration=production
```
Les fichiers optimisés seront disponibles dans le dossier `dist/`.

---

## Contact
Pour toute question ou problème, contactez l'équipe à l'adresse suivante : `support@yoga-app.com`. 
