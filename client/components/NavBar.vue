<template>
  <div class="fixed right-0 hidden px-3 py-4 font-bold font-styled sm:block">
    <NuxtLink
      v-for="(link, index) in links"
      :key="index"
      :to="{ name: link.path }"
      class="grid items-center grid-cols-2 space-x-5 hover:underline hover:text-emerald-500"
    >
      <div class="text-right">{{ link.name }}</div>
      <ig-icon
        no-color
        size="sm"
        :name="link.icon"
        class="text-black"
      ></ig-icon>
    </NuxtLink>
  </div>
</template>

<script>
export default {
  data() {
    return {
      links: [
        { name: 'Home', path: 'index', icon: 'home' },
        { name: 'Register', path: 'accounts-register', icon: 'key' },
        { name: 'Login', path: 'accounts-login', icon: 'lock' },
        { name: 'Logout', path: 'accounts-logout', icon: 'unlock' },
      ],
    }
  },
}
</script>
