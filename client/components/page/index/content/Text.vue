<template>
  <div
    class="flex flex-col items-center justify-center w-full space-y-10 sm:w-1/2 sm:space-y-14"
  >
    <div class="space-y-10">
      <div
        class="w-full text-3xl font-bold sm:text-4xl font-poppins text-dark-black"
      >
        Lorem ipsum dolor, sit amet consectetur adipisicing elit. Repellendus,
        soluta?
      </div>
      <div class="w-11/12 leading-7 font-poppins">
        Lorem ipsum dolor sit amet consectetur adipisicing elit. In, quam error
        tempore aliquam expedita quis. Debitis nisi, consequuntur nesciunt
        facilis distinctiovero laborum ducimus ipsum repellat totam animi
        aliquam minus blanditiis reprehenderit cumque modi! Eos culpa minima
        iusto doloremqu!
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
