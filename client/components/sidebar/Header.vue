<template>
  <div
    class="flex items-center px-5 py-2 mb-5 space-x-4 border-[#C4C4C4] border bg-[#F5F5F7]"
  >
    <img
      :src="$auth.user.imageURL"
      alt="profile image"
      class="w-12 h-12 rounded-3xl"
    />

    <div class="leading-none truncate font-styled-code">
      <div class="text-lg text-black">
        {{ $auth.user.firstName }}
      </div>
      <div>{{ $auth.user.lastName }}</div>
    </div>
  </div>
</template>

<script>
export default {}
</script>
