#!/bin/bash

# Script de test de syntaxe Java (sans compilation)
# Vérifie que le code Java est syntaxiquement correct

echo "🔍 Vérification de la syntaxe Java..."

# Vérifier que tous les fichiers Java existent
java_files=(
    "src/Main.java"
    "src/controller/QRCodeController.java"
    "src/model/QRCodeModel.java"
    "src/model/UserProfile.java"
    "src/view/QRCodeView.java"
    "src/utils/QRCodeGeneratorUtil.java"
    "src/utils/PDFGeneratorUtil.java"
    "src/utils/ProjectManager.java"
)

missing_files=()

for file in "${java_files[@]}"; do
    if [ ! -f "$file" ]; then
        missing_files+=("$file")
    fi
done

if [ ${#missing_files[@]} -gt 0 ]; then
    echo "❌ Fichiers manquants:"
    for file in "${missing_files[@]}"; do
        echo "   - $file"
    done
    exit 1
fi

echo "✅ Tous les fichiers Java sont présents"

# Vérifier la syntaxe basique (parenthèses, accolades, etc.)
echo "🔍 Vérification de la syntaxe basique..."

syntax_errors=()

for file in "${java_files[@]}"; do
    # Vérifier les parenthèses équilibrées
    open_paren=$(grep -o '(' "$file" | wc -l)
    close_paren=$(grep -o ')' "$file" | wc -l)
    
    if [ "$open_paren" -ne "$close_paren" ]; then
        syntax_errors+=("$file: Parenthèses non équilibrées ($open_paren ouverts, $close_paren fermés)")
    fi
    
    # Vérifier les accolades équilibrées
    open_brace=$(grep -o '{' "$file" | wc -l)
    close_brace=$(grep -o '}' "$file" | wc -l)
    
    if [ "$open_brace" -ne "$close_brace" ]; then
        syntax_errors+=("$file: Accolades non équilibrées ($open_brace ouvertes, $close_brace fermées)")
    fi
    
    # Vérifier les points-virgules manquants à la fin des lignes importantes
    if grep -q "private.*=.*new.*[^;]$" "$file"; then
        syntax_errors+=("$file: Point-virgule manquant possible")
    fi
done

if [ ${#syntax_errors[@]} -gt 0 ]; then
    echo "❌ Erreurs de syntaxe détectées:"
    for error in "${syntax_errors[@]}"; do
        echo "   - $error"
    done
    exit 1
fi

echo "✅ Syntaxe basique correcte"

# Vérifier les imports et packages
echo "🔍 Vérification des imports..."

for file in "${java_files[@]}"; do
    # Vérifier que les fichiers ont un package déclaré
    if ! grep -q "^package " "$file"; then
        if [ "$file" != "src/Main.java" ]; then
            syntax_errors+=("$file: Pas de déclaration de package")
        fi
    fi
done

if [ ${#syntax_errors[@]} -gt 0 ]; then
    echo "❌ Problèmes de structure détectés:"
    for error in "${syntax_errors[@]}"; do
        echo "   - $error"
    done
    exit 1
fi

echo "✅ Structure des packages correcte"

# Vérifier les méthodes principales
echo "🔍 Vérification des méthodes principales..."

# Vérifier Main.java
if ! grep -q "public static void main" "src/Main.java"; then
    echo "❌ Méthode main manquante dans Main.java"
    exit 1
fi

# Vérifier QRCodeView.java
if ! grep -q "public QRCodeView()" "src/view/QRCodeView.java"; then
    echo "❌ Constructeur manquant dans QRCodeView.java"
    exit 1
fi

# Vérifier QRCodeController.java
if ! grep -q "public QRCodeController" "src/controller/QRCodeController.java"; then
    echo "❌ Constructeur manquant dans QRCodeController.java"
    exit 1
fi

echo "✅ Méthodes principales présentes"

echo ""
echo "🎉 Vérification terminée avec succès !"
echo "📝 Le code Java semble syntaxiquement correct"
echo ""
echo "⚠️  Pour compiler et exécuter, vous devez installer Java JDK:"
echo "   - macOS: brew install openjdk@11"
echo "   - Ubuntu: sudo apt install openjdk-11-jdk"
echo "   - Windows: Télécharger depuis https://adoptium.net/"
echo ""
echo "🚀 Une fois Java installé, utilisez: ./compile.sh"
