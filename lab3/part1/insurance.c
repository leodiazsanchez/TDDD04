#include <stdio.h>
#include </courses/TDDD04/symbolic/include/klee/klee.h>

/*
 * Calculate the deductible for the client
 * Base cost is 5000 SEK if the client is above 30 or has had a driving licence for more that 5 years and 8000 SEK otherwise
 * With every accident for that calendar the deductible increases:
 * 1 accident  : by 1000 SEK
 * 2 accidents : by 2500 SEK
 * 3 accidents : by 4000 SEK
 * 4 accidents and more by : 10000 SEK
 * If the client is a gold member, then for the first 2 accidents, there is no increase
 * but for 3 accidents and more normal rates apply
 */

int getClientDeductible(int age, int yearOfLicence, int numberOfAccidents, int isGoldMember)
{
    if (0 > numberOfAccidents || age < 18 || age > 149)
    {
        return -1;
    }

    int cost = (age > 30 || yearOfLicence > 5) ? 5000 : 8000;

    if (numberOfAccidents == 1 && isGoldMember == 0)
    {
        cost += 1000;
    }
    else if (numberOfAccidents == 2 && isGoldMember == 0)
    {
        cost += 2500;
    }
    else if (numberOfAccidents == 3)
    {
        cost += 4000;
    }
    else if (numberOfAccidents >= 4)
    {
        cost += 10000;
    }

    return cost;
}

int main()
{
    int age, yearOfLicence, numberOfAccidents, isGoldMember;
    int result = 0;

    klee_make_symbolic(&age, sizeof(age), "age");
    klee_make_symbolic(&yearOfLicence, sizeof(yearOfLicence), "yearOfLicence");
    klee_make_symbolic(&numberOfAccidents, sizeof(numberOfAccidents), "numberOfAccidents");
    klee_make_symbolic(&isGoldMember, sizeof(isGoldMember), "isGoldMember");

    result = getClientDeductible(age, yearOfLicence, numberOfAccidents, isGoldMember);

    if (0 > numberOfAccidents || age < 18 || age > 149){
        klee_assert(result == -1);
    } else {
        klee_assert(result >= 5000 && 18000 >= result);
    }

    return getClientDeductible(age, yearOfLicence, numberOfAccidents, isGoldMember);
}
