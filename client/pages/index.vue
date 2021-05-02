<template>
  <div class="w-full h-screen">
    <div class="flex flex-col w-full h-full px-4 mx-auto sm:w-10/12 2xl:w-9/12">
      <div class="flex flex-wrap flex-grow space-y-10 sm:space-y-0">
        <div
          class="flex flex-col items-center justify-center w-full space-y-10 sm:w-1/2 sm:space-y-14"
        >
          <div class="space-y-10">
            <div
              class="w-full text-3xl font-bold sm:text-4xl font-poppins text-dark-black"
            >
              Lorem ipsum dolor, sit amet consectetur adipisicing elit.
              Repellendus, soluta?
            </div>
            <div class="w-11/12 leading-7 font-poppins">
              Lorem ipsum dolor sit amet consectetur adipisicing elit. In, quam
              error tempore aliquam expedita quis. Debitis nisi, consequuntur
              nesciunt facilis distinctiovero laborum ducimus ipsum repellat
              totam animi aliquam minus blanditiis reprehenderit cumque modi!
              Eos culpa minima iusto doloremqu!
            </div>
          </div>
          <NuxtLink
            :to="{ name: 'accounts-register' }"
            class="flex justify-center px-5 py-4 space-x-3 text-white transition duration-300 transform font-poppins rounded-15px hover:scale-105 sm:py-3 focus:outline-none bg-primary ring-black ring-1"
          >
            Sign up for Resourceium
          </NuxtLink>
        </div>
        <div class="flex items-center justify-center w-full sm:w-1/2">
          <img
            :src="require('~/assets/images/landing-page.png')"
            alt="landing page image"
            rel="preload"
          />
        </div>
      </div>
      <div class="w-full h-0.5 bg-gray-400 rounded-full"></div>
      <div class="flex justify-between flex-none px-5 my-10 font-styled-code">
        <div
          v-for="index in 3"
          :key="index"
          class="px-5 py-3 border border-black"
        >
          Stat
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {}
</script>
