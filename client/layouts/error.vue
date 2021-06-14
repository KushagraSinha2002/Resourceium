<template>
  <div class="flex items-center justify-center w-full h-full px-5">
    <div class="flex flex-col space-y-8">
      <h1
        class="text-center text-red-500 text-7xl lg:text-9xl font-styled-code"
      >
        [{{ error.statusCode }}]
      </h1>
      <h2 class="text-2xl lg:text-4xl text-warm-gray-900 font-poppins">
        {{ error.message }}
      </h2>
      <NuxtLink
        :to="{ name: 'index' }"
        class="flex items-center mx-auto space-x-3 group"
      >
        <ig-icon
          name="home"
          size="xl"
          no-color
          variant="success"
          class="transition-transform duration-300 transform text-warm-gray-600 group-hover:scale-105"
        ></ig-icon>
        <div
          class="text-xl text-warm-gray-600 font-poppins group-hover:underline"
        >
          Go Back Home
        </div>
      </NuxtLink>
    </div>
  </div>
</template>

<script>
export default {
  props: { error: { type: Object, required: true } },
}
</script>
