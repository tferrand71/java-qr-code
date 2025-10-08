# Générateur de QR Code - Application Java

## Description
Application Java Swing pour générer des QR Codes avec personnalisation et export en document PDF.

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
- **Fichier principal** : PDF :
  - Titre du document
  - Contenu encodé
  - QR Code généré
  - Instructions d'utilisation
  - Logo personnalisé 

## Fonctionnalités Techniques

### Génération QR Code Simplifiée
- Utilise un algorithme de génération basique 
- Crée un motif QR Code avec marqueurs de position

### Export Document
- Support des couleurs personnalisées
- Intégration du logo utilisateur
- Format PDF pour compatibilité maximale
