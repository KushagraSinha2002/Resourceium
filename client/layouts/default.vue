<template>
  <div :class="mode !== 'production' ? 'debug-screens' : ''">
    <Alerts></Alerts>
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
