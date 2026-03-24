# 🚀 QUICK DEPLOYMENT CHECKLIST

## Before You Start
- [ ] Have GitHub account ready
- [ ] Have Render.com account ready  
- [ ] Know your frontend domain (sitelive.in)

## File Preparation (5 minutes)
- [ ] Copy ALL Java files from local version to new folder
- [ ] Replace these 4 files with fixed versions:
  - [ ] pom.xml
  - [ ] application.properties  
  - [ ] SecurityConfig.java
  - [ ] UserPrincipal.java
- [ ] Add Dockerfile
- [ ] Add .dockerignore
- [ ] Add .gitignore

## GitHub Setup (2 minutes)
- [ ] git init
- [ ] git add .
- [ ] git commit -m "Initial commit"
- [ ] Create GitHub repo
- [ ] git push to GitHub

## Render Database (3 minutes)
- [ ] Create PostgreSQL database
- [ ] Copy Internal Database URL
- [ ] Save it somewhere safe

## Render Backend (5 minutes)
- [ ] Create Web Service
- [ ] Connect GitHub repo
- [ ] Set Runtime to Docker
- [ ] Add Environment Variables:
  - [ ] DATABASE_URL
  - [ ] FRONTEND_URL  
  - [ ] JWT_SECRET
  - [ ] PORT
- [ ] Click Deploy

## Monitor (10 minutes)
- [ ] Watch build logs
- [ ] Wait for "BUILD SUCCESS"
- [ ] Wait for "Started SecurityExApplication"
- [ ] Copy backend URL

## Frontend Update (2 minutes)
- [ ] Update API_BASE_URL in React
- [ ] Deploy frontend to Hostinger

## Test (2 minutes)
- [ ] Test /login endpoint
- [ ] Test from frontend
- [ ] Check CORS works

## ✅ TOTAL TIME: ~30 minutes

---

## 🆘 COMMON ERRORS & FIXES

### "Cannot find symbol: PasswordEncoder"
→ Missing import. Already fixed in new SecurityConfig.java

### "Duplicate bean 'authenticationProvider'"  
→ Two beans with same name. Already fixed in new SecurityConfig.java

### "package org.jspecify.annotations does not exist"
→ Bad dependency. Already removed from new pom.xml

### "Database connection failed"
→ Check DATABASE_URL is the Internal URL from Render PostgreSQL

### "CORS error"
→ Check FRONTEND_URL matches exactly (no trailing slash)

---

## 📱 EMERGENCY CONTACT

If stuck after following all steps:
1. Screenshot the exact error from Render logs
2. Check which step failed
3. Re-read that section in DEPLOYMENT_GUIDE.md
