#!/bin/bash

# Script d'installation automatique de Java pour macOS
# Usage: ./install_java.sh

echo "🚀 Installation de Java pour le Générateur de QR Code"
echo "=================================================="

# Vérifier si Java est déjà installé
if command -v java &> /dev/null; then
    echo "✅ Java est déjà installé:"
    java -version
    echo ""
    echo "🎉 Vous pouvez maintenant compiler l'application avec: ./compile.sh"
    exit 0
fi

echo "❌ Java n'est pas installé. Installation en cours..."

# Vérifier si Homebrew est installé
if ! command -v brew &> /dev/null; then
    echo "⚠️  Homebrew n'est pas installé. Installation de Homebrew..."
    /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
    
    # Ajouter Homebrew au PATH pour Apple Silicon
    if [[ $(uname -m) == "arm64" ]]; then
        echo 'eval "$(/opt/homebrew/bin/brew shellenv)"' >> ~/.zshrc
        eval "$(/opt/homebrew/bin/brew shellenv)"
    fi
fi

echo "📦 Installation d'OpenJDK 11 via Homebrew..."
brew install openjdk@11

# Ajouter Java au PATH
JAVA_PATH=""
if [[ $(uname -m) == "arm64" ]]; then
    # Apple Silicon
    JAVA_PATH="/opt/homebrew/opt/openjdk@11/bin:$PATH"
else
    # Intel
    JAVA_PATH="/usr/local/opt/openjdk@11/bin:$PATH"
fi

echo "🔧 Configuration du PATH..."
echo "export PATH=\"$JAVA_PATH\"" >> ~/.zshrc
export PATH="$JAVA_PATH"

echo "🔄 Rechargement du shell..."
source ~/.zshrc 2>/dev/null || true

# Vérifier l'installation
echo ""
echo "🔍 Vérification de l'installation..."
if command -v java &> /dev/null; then
    echo "✅ Java installé avec succès !"
    java -version
    echo ""
    echo "🎉 Installation terminée !"
    echo ""
    echo "📝 Prochaines étapes:"
    echo "   1. Compiler l'application: ./compile.sh"
    echo "   2. Exécuter l'application: java -cp bin Main"
    echo ""
else
    echo "❌ Erreur lors de l'installation"
    echo ""
    echo "🔧 Solutions alternatives:"
    echo "   1. Redémarrez votre terminal"
    echo "   2. Ou installez manuellement depuis: https://adoptium.net/"
    echo "   3. Ou utilisez: brew install openjdk@11"
fi
