<script setup lang="ts">
const appTitle = import.meta.env.QL_PUBLIC_APP_TITLE
</script>

<template>
  <main class="home">
    <p class="home__name">QINGLAN</p>
    <h1>{{ appTitle }}</h1>
    <p class="home__intro">让 AI 成为理解你的个人智能伙伴。</p>
    <p class="home__note">从这里开始，慢慢成长。</p>
  </main>
</template>

<style scoped>
.home {
  width: 100%;
  max-width: 48rem;
  margin: auto;
  padding: 4rem 1.5rem;
}

.home__name {
  margin: 0 0 1rem;
  color: var(--color-accent);
  font-size: 0.75rem;
  font-weight: 600;
  letter-spacing: 0.25em;
}

h1 {
  margin: 0 0 1.5rem;
  font-size: clamp(2.5rem, 6vw, 4rem);
  font-weight: 600;
  line-height: 1.2;
}

.home__intro {
  margin: 0;
  font-size: 1.125rem;
}

.home__note {
  margin: 2rem 0 0;
  color: var(--color-text-muted);
  font-size: 0.875rem;
}
</style>
