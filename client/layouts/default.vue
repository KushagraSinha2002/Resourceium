<template>
  <div :class="mode !== 'production' ? 'debug-screens' : ''">
    <alerts-container></alerts-container>
    <nav-bar></nav-bar>
    <Nuxt />
  </div>
</template>

<script>
export default {
  data: () => ({
    mode: process.env.NODE_ENV,
  }),
}
</script>
