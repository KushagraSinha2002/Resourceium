<template>
  <NuxtLink
    :to="{
      name: 'socials-users-username',
      params: { username: `@${user.value0}` },
    }"
    class="flex items-center px-3 py-2 space-x-3 leading-tight transition-shadow group bg-warm-gray-100 rounded-15px hover:shadow-lg"
  >
    <img
      :src="user.value1"
      :alt="`${user.value0} image`"
      class="object-cover rounded-full h-14 w-14"
    />
    <div>
      <div
        data-type="username"
        class="text-lg font-semibold text-gray-900 group-hover:underline font-poppins"
      >
        {{ user.value0 }}
      </div>
      <div data-type="following" class="text-sm text-gray-600 font-poppins">
        Follows
        <span class="text-base text-gray-800">{{ user.value2 }}</span> user(s)
      </div>
      <div data-type="followers" class="text-sm text-gray-600 font-poppins">
        Followed by
        <span class="text-base text-gray-800">{{ user.value3 }}</span> user(s)
      </div>
    </div>
  </NuxtLink>
</template>

<script>
export default {
  props: { user: { type: Object, required: true } },
}
</script>
