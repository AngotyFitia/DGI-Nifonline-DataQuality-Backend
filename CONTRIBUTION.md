# Liste des contributions

Ce document retrace l'évolution du projet DGI et les principales contributions réalisées au fil des Pull Requests.

---

## PR #1 - Mise en place du système d'authentification et sécurisation API 

- Description :
  - Mise en place de backend inscription
  - Implémentation de la gestion des tokens
  - Implémentation de la gestion des connexions, déconnexion, revocations
  - Implémentation de la gestion de sécurisation(recaptcha pour la vérification humaine)

- Lien : https://github.com/AngotyFitia/DGI-Nifonline-DataQuality-Backend/pull/1


## PR #2 - Déploiement

- Description :
  - Ajout du fichier Dockerfile pour la conteneurisation du projet

- Lien : https://github.com/AngotyFitia/DGI-Nifonline-DataQuality-Backend/pull/2

## PR #3 - Exceptions

- Description :
  - Ajout des messages d'erreurs pour les champs nulls

- Lien : https://github.com/AngotyFitia/DGI-Nifonline-DataQuality-Backend/pull/3

## PR #4 - Gestion des utilisateurs
  - mise en place complète de la gestion des utilisateurs avec filtres, pagination, activation/désactivation, DTO enrichi, et ajout des KPI utilisateurs.

- Lien : https://github.com/AngotyFitia/DGI-Nifonline-DataQuality-Backend/pull/4

## PR #5 - Gestion des erreurs de deploiement
- Lien : https://github.com/AngotyFitia/DGI-Nifonline-DataQuality-Backend/pull/5

## PR #6 - Gestion de la securite applicative
  - Mise en place de système de détection de tentatives suspects
  - Uniformisation des messages d’erreur backend pour une meilleure intégration frontend.
  - Mise à jour du GlobalExceptionHandler pour renvoyer { message, type } en cas d’exception personnalisée.
- Lien : https://github.com/AngotyFitia/DGI-Nifonline-DataQuality-Backend/pull/6