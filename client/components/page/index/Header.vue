<template>
  <div class="flex-none w-full py-3 bg-[#F5F5F7] flex">
    <div
      class="flex items-center justify-between w-full mx-auto sm:w-9/12 2xl:w-8/12"
    >
      <NuxtLink
        :to="{ name: 'index' }"
        class="font-thin font-poppins hover:underline"
      >
        Why Resourceium?
      </NuxtLink>
      <div class="flex items-center space-x-3 font-semibold sm:space-x-5">
        <NuxtLink
          class="text-primary font-poppins hover:underline"
          :to="{ name: 'accounts-login' }"
        >
          Sign In
        </NuxtLink>
        <NuxtLink
          class="text-primary font-poppins hover:underline"
          :to="{ name: 'accounts-register' }"
        >
          Sign Up
        </NuxtLink>
      </div>
    </div>
  </div>
</template>
