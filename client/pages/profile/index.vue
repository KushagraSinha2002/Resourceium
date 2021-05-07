<template>
  <div>
    <div>Profile</div>
    <div>{{ $auth.user }}</div>
  </div>
</template>

<script>
export default {
  middleware: ['auth'],
}
</script>
