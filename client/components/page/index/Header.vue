<template>
  <div class="flex-none w-full py-3 bg-[#F5F5F7] flex">
    <div
      class="flex items-center justify-between w-full px-6 mx-auto sm:w-9/12 2xl:w-8/12"
    >
      <NuxtLink
        id="link-faq"
        :to="{ name: 'faq' }"
        class="font-thin font-poppins hover:underline"
      >
        Why Resourceium?
      </NuxtLink>
      <div>
        <div
          v-if="!$auth.loggedIn"
          class="flex items-center space-x-3 font-semibold sm:space-x-5"
        >
          <NuxtLink
            id="link-login"
            class="text-primary font-poppins hover:underline"
            :to="{ name: 'accounts-login' }"
          >
            Sign In
          </NuxtLink>
          <NuxtLink
            id="link-register"
            class="text-primary font-poppins hover:underline"
            :to="{ name: 'accounts-register' }"
          >
            Sign Up
          </NuxtLink>
        </div>
        <div v-else>
          <NuxtLink
            id="link-dashboard"
            class="text-primary font-poppins hover:underline"
            :to="{ name: 'dashboard' }"
          >
            Dashboard
          </NuxtLink>
        </div>
      </div>
    </div>
  </div>
</template>
