#!/bin/bash

# Script de compilation pour le générateur de QR Code
# Assurez-vous que Java JDK est installé sur votre système

echo "Compilation du générateur de QR Code..."

# Créer le dossier bin s'il n'existe pas
mkdir -p bin

# Compiler tous les fichiers Java
javac -d bin -cp src src/*.java src/controller/*.java src/model/*.java src/view/*.java src/utils/*.java

if [ $? -eq 0 ]; then
    echo "✅ Compilation réussie !"
    echo ""
    echo "Pour exécuter l'application :"
    echo "java -cp bin Main"
    echo ""
else
    echo "❌ Erreur de compilation"
    exit 1
fi
