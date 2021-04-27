<template>
  <div :class="mode !== 'production' ? 'debug-screens' : ''">
    <alerts></alerts>
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
