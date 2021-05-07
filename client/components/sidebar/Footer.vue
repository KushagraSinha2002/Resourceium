<template>
  <NuxtLink
    :to="{ name: 'accounts-logout' }"
    class="flex items-center justify-center px-2 py-1 space-x-8 md:py-3 rounded-t-xl from-red-700 bg-gradient-to-t to-sunset-600"
  >
    <ig-icon name="power" variant="black" size="lg"></ig-icon>
    <div class="text-xl font-bold font-poppins">Logout</div>
  </NuxtLink>
</template>
