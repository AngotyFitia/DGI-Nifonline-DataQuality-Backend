# Décisions techniques et de conception Backend — DGI Data Quality

Ce document présente les principales décisions prises lors de la conception et du développement du backend Spring Boot du projet Plateforme intelligente d’aide au contrôle de qualité du registre des contribuables.

---

## 2026-07-15 — Utilisation de JPA/Hibernate pour la persistance

### Raisons
- Simplifier l’accès aux données avec des entités Java mappées aux tables PostgreSQL.  
- Réduire le code SQL manuel grâce aux repositories Spring Data.  
- Faciliter l’évolution du schéma avec `ddl-auto=update`.  

### Impact
- Gain de productivité et cohérence entre modèle objet et base.  
- Maintenance simplifiée (moins de requêtes SQL à écrire).  
- Risque de dépendance forte à Hibernate (attention aux optimisations nécessaires).

---

## 2026-07-16 — Intégration de Google reCAPTCHA pour l’authentification

### Raisons
- Prévenir les attaques automatisées (brute force, bots).  
- Sécuriser les formulaires de login et d’inscription.  
- Standard reconnu et facile à intégrer côté frontend et backend.  

### Impact
- Amélioration de la sécurité des endpoints sensibles.  
- Complexité supplémentaire côté frontend (gestion du token reCAPTCHA).  
- Dépendance à un service externe (Google).

---

## 2026-07-17 — Gestion des tokens de session et révocation

### Raisons
- Permettre la déconnexion explicite des utilisateurs.  
- Stocker les tokens actifs en base (`session_token`) pour contrôler leur validité.  
- Pouvoir invalider un token avant son expiration (revocation).  

### Impact
- Sécurité renforcée (un utilisateur peut être déconnecté à distance).  
- Complexité accrue (il faut gérer la table `session_token`).  
- Nécessité de synchroniser JWT et base (revocation = suppression du token en DB).

---

## 2026-07-17 — Hébergement du backend sur Render et base de données sur Supabase

### Raisons
- **Render (Backend)**  
  - Plateforme simple et intégrée pour déployer des applications Spring Boot avec Docker.  
  - Support natif des variables d’environnement pour gérer les secrets (JWT, reCAPTCHA, credentials DB).  
  - Déploiement continu via GitHub, ce qui facilite la mise en production.  
  - Offre gratuite suffisante pour les premiers tests, évolutive vers des plans payants.  

- **Supabase (Base de données)**  
  - Fournit une instance PostgreSQL complète et fiable, accessible en ligne.  
  - Interface moderne avec SQL Editor pour gérer le schéma et les données.  
  - Offre gratuite généreuse, adaptée aux projets en phase de développement.  
  - Possibilité d’activer Row Level Security si un jour on veut exposer l’API Supabase directement au frontend.  

### Impact
- **Séparation claire des responsabilités** : Render gère l’application, Supabase gère la base.  
- **Scalabilité** : possibilité de monter en puissance indépendamment pour le backend et la base.  
- **Sécurité** : gestion des secrets via Render, base protégée par Supabase avec SSL obligatoire.  
- **Flexibilité** : facile à remplacer ou migrer l’un des deux services si besoin.  


