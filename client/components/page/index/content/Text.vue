<template>
  <div
    class="flex flex-col items-center justify-center w-full space-y-10 sm:w-1/2 sm:space-y-14"
  >
    <div class="space-y-10">
      <div
        class="w-full text-3xl font-bold sm:text-4xl font-poppins text-dark-black"
      >
        Your abode for anything and everything
      </div>
      <div class="w-11/12 leading-7 font-poppins">
      Anything and everything that can be represented in a digital format, can be shared as a resource here.
      Find what you want or share what you have. A community where you both provide help and get help from.
      We look forward to your valuable addition.
      </div>
    </div>
    <NuxtLink
      :to="{ name: 'accounts-register' }"
      class="flex justify-center px-5 py-4 space-x-3 text-white transition duration-300 transform font-poppins rounded-15px hover:scale-105 sm:py-3 focus:outline-none bg-primary ring-black ring-1"
    >
      Sign up for Resourceium
    </NuxtLink>
  </div>
</template>
