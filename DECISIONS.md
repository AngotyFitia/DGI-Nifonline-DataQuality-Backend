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

## 2026-07-15 — Intégration de Google reCAPTCHA pour l’authentification

### Raisons
- Prévenir les attaques automatisées (brute force, bots).  
- Sécuriser les formulaires de login et d’inscription.  
- Standard reconnu et facile à intégrer côté frontend et backend.  

### Impact
- Amélioration de la sécurité des endpoints sensibles.  
- Complexité supplémentaire côté frontend (gestion du token reCAPTCHA).  
- Dépendance à un service externe (Google).

---

## 2026-07-15 — Gestion des tokens de session et révocation

### Raisons
- Permettre la déconnexion explicite des utilisateurs.  
- Stocker les tokens actifs en base (`session_token`) pour contrôler leur validité.  
- Pouvoir invalider un token avant son expiration (revocation).  

### Impact
- Sécurité renforcée (un utilisateur peut être déconnecté à distance).  
- Complexité accrue (il faut gérer la table `session_token`).  
- Nécessité de synchroniser JWT et base (revocation = suppression du token en DB).

