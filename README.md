# 🤖 AutoPilotAI

**An AI agent that autonomously deploys, monitors, and self-heals a Java application.**.

---

## 📸 Application Overview

<img width="912" height="447" alt="SS" src="https://github.com/user-attachments/assets/2a5bfe40-6d17-4de7-a32b-a59e32acbcab" />

---

## 🚀 Getting Started

---

# 🧩 PHASE 1 — FOUNDATION (TARGET APPLICATION)

🎯 Goal:

Create a simple Spring Boot Java application that:

Has one REST endpoint

Can run inside a Docker container

---

# 🧩 PHASE 2 — Monitoring Layer (MONITORING HEARTBEAT OF THE APPLICATION)

🎯 Goal:

Add Spring Boot Actuator to the app so that it exposes:

/actuator/health → tells if the app is running fine

/actuator/metrics → shows CPU, memory, requests, etc.

/actuator/loggers → lets us view and modify log levels dynamically

---

# 🧩 PHASE 3 — Agent Setup (BUILDING THE AUTOPILOT)

🎯 Goal:

Create a Java-based agent service (a second Spring Boot app) that:

Periodically calls the target app’s /actuator/health and /metrics endpoints

Detects if the app is unhealthy or has degraded performance

Logs findings for now (later, this will evolve into AI reasoning + self-healing logic)

---

# 🧩 PHASE 4 — AI Reasoning Setup (ADDING BRAIN TO THE AUTOPILOT)

🎯 Goal:

Make Autopilot Agent use an LLM (Large Language Model) to:

Interpret the health data (/actuator/health JSON responses)

Summarize what’s happening (e.g., “App is healthy” or “Disk space low”)

Suggest likely causes or recommended actions (in natural language)

This transforms our agent from a monitoring tool → into an AI-assisted observer 🧠

---

# 🧩 PHASE 5 — AUTO FIX + REDEPLOY (CORE AGENTIC BEHAVIOR)

🎯 What we will build in Phase 5

Our Autopilot Agent will now:

✅ Detect a failure (app DOWN, health != UP)

🔁 Try up to N recovery strategies (e.g. restart container, rebuild image)

🔍 Verify after each attempt (health check again)

🧠 Ask AI whether recovery succeeded

📄 Generate a final developer report

---

# 🧩 PHASE 6 — AI-Guided Code Fixes (SELF HEALING)

🎯 Where the agent will be able to:

✅ Read stack traces

❓ Ask AI how to fix them

🧠 Apply code patches

🧪 Test them

🔁 Redeploy

📄 Provide a PR-like summary

---

## 💡 Author

Aditya Upadhyaya

GitHub: [123-Aditya](https://github.com/123-Aditya)
