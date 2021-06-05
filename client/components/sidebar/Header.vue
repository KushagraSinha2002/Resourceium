<template>
  <div
    class="flex items-center px-5 py-2 mb-5 space-x-4 border-[#C4C4C4] border bg-[#F5F5F7]"
  >
    <img
      :src="require('~/assets/images/id_logo.png')"
      alt="profile image"
      class="w-12 h-12"
    />

    <div class="leading-none truncate font-styled-code">
      <div class="text-lg text-black">
        {{ $auth.user.firstName }}
      </div>
      <div>{{ $auth.user.lastName }}</div>
      <!-- <div class="text-xs text-warm-gray-700 font-poppins">
        {{ $auth.user.email }}
      </div> -->
    </div>
  </div>
</template>

<script>
export default {}
</script>
