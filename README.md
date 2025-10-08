# Générateur de QR Code - Application Java

## Description
Application Java Swing pour générer des QR Codes avec personnalisation et export en document image.

## Fonctionnalités
- ✅ Génération de QR Code 
- ✅ Interface graphique intuitive avec onglets
- ✅ Aperçu en temps réel du QR Code
- ✅ Personnalisation des couleurs et polices
- ✅ Export en document PDF
- ✅ Gestion des logos personnalisés
- ✅ Sauvegarde et chargement de projets

## Structure du Projet

```
src/
├── Main.java                    # Point d'entrée de l'application
├── controller/
│   └── QRCodeController.java    # Contrôleur MVC
├── model/
│   ├── QRCodeModel.java         # Modèle de données
│   └── UserProfile.java         # Profil utilisateur
├── view/
│   └── QRCodeView.java          # Interface graphique
└── utils/
    ├── QRCodeGeneratorUtil.java # Génération QR Code simplifiée
    ├── PDFGeneratorUtil.java    # Export document (image composite)
    └── ProjectManager.java      # Gestion des projets
```

## Utilisation

### 1. Génération de QR Code
1. Ouvrez l'onglet "📄 Génération Document"
2. Saisissez le texte ou URL à encoder
3. Choisissez le chemin de sauvegarde
4. Ajustez les dimensions du QR Code
5. Cliquez sur "👁️ Aperçu QR Code" pour prévisualiser
6. Cliquez sur "📄 Générer Document Complet" pour créer le fichier final

### 2. Personnalisation
1. Ouvrez l'onglet "🎨 Personnalisation Document"
2. Sélectionnez la police et la taille
3. Choisissez les couleurs du texte et du fond
4. Ajoutez un logo personnalisé (optionnel)

### 3. Format de Sortie
- **Fichier principal** : Image PNG composite (800x600px) contenant :
  - Titre du document
  - Contenu encodé
  - QR Code généré
  - Instructions d'utilisation
  - Logo personnalisé (si ajouté)

## Fonctionnalités Techniques

### Génération QR Code Simplifiée
- Utilise un algorithme de génération basique sans bibliothèque externe
- Crée un motif QR Code avec marqueurs de position
- Basé sur le hash du texte pour la génération du motif

### Export Document
- Crée une image composite avec toutes les informations
- Support des couleurs personnalisées
- Intégration du logo utilisateur
- Format PNG pour compatibilité maximale

### Gestion des Projets
- Sérialisation des modèles et profils
- Sauvegarde au format `.qrproj` et `.qrprof`
- Chargement des configurations précédentes

## Dépannage

### Erreur "Unable to locate Java Runtime"
- Installez Java JDK depuis [Oracle](https://www.oracle.com/java/technologies/downloads/) ou [OpenJDK](https://openjdk.org/)
- Vérifiez que `JAVA_HOME` est configuré correctement

### Erreur de compilation
- Vérifiez que tous les fichiers Java sont présents dans le dossier `src/`
- Assurez-vous d'utiliser Java 8 ou supérieur
- Vérifiez les permissions d'écriture dans le dossier `bin/`

### Interface ne s'affiche pas
- Vérifiez que les composants Swing sont disponibles
- Sur Linux, installez les paquets graphiques nécessaires

## Limitations
- QR Code généré est un motif simplifié (non standard)
- Pas de vraie génération PDF (remplacée par image composite)
- Fonctionnalité de scan limitée (motif basique)

## Développement
Pour améliorer l'application :
1. Intégrer une vraie bibliothèque QR Code (ZXing)
2. Ajouter la génération PDF réelle (iText)
3. Implémenter la lecture de QR Codes
4. Ajouter plus d'options de personnalisation

## Licence
Projet éducatif - BTS SIO
