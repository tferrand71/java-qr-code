# Installation Java pour le Générateur de QR Code

## Problème actuel
❌ **Java n'est pas installé** sur votre système, ce qui empêche la compilation et l'exécution de l'application.

## Solutions d'installation

### 🍎 macOS (votre système)
```bash
# Option 1: Homebrew (recommandé)
brew install openjdk@11

# Option 2: Téléchargement direct
# Visitez: https://adoptium.net/
# Téléchargez OpenJDK 11 pour macOS
```

### 🐧 Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install openjdk-11-jdk
```

### 🪟 Windows
1. Visitez https://adoptium.net/
2. Téléchargez OpenJDK 11 pour Windows
3. Installez en suivant les instructions

## Vérification après installation

### 1. Vérifier Java
```bash
java -version
javac -version
```

Vous devriez voir quelque chose comme :
```
openjdk version "11.0.16" 2022-07-19
OpenJDK Runtime Environment 18.9 (build 11.0.16+8-LTS)
OpenJDK 64-Bit Server VM 18.9 (build 11.0.16+8-LTS, mixed mode)
```

### 2. Compiler l'application
```bash
./compile.sh
```

### 3. Exécuter l'application
```bash
java -cp bin Main
```

## Résolution des problèmes

### Erreur "Unable to locate Java Runtime"
- Java n'est pas installé ou pas dans le PATH
- Réinstallez Java et redémarrez le terminal

### Erreur "Permission denied" sur les scripts
```bash
chmod +x compile.sh test_syntax.sh
```

### Erreur de compilation
- Vérifiez que Java 8+ est installé
- Vérifiez les permissions d'écriture dans le dossier `bin/`

## Alternative : Installation automatique (macOS)
```bash
# Si vous avez Homebrew
if ! command -v java &> /dev/null; then
    echo "Installation de Java..."
    brew install openjdk@11
    echo 'export PATH="/opt/homebrew/opt/openjdk@11/bin:$PATH"' >> ~/.zshrc
    source ~/.zshrc
    echo "Java installé avec succès !"
else
    echo "Java est déjà installé"
fi
```

## Test rapide
```bash
# Test de syntaxe (fonctionne sans Java)
./test_syntax.sh

# Compilation (nécessite Java)
./compile.sh

# Exécution (nécessite Java)
java -cp bin Main
```

## Support
Si vous rencontrez des problèmes :
1. Vérifiez que Java est bien installé : `java -version`
2. Vérifiez les permissions des scripts : `ls -la *.sh`
3. Consultez le README.md pour plus d'informations
