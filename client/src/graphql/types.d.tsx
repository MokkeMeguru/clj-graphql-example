import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
export type Maybe<T> = T | null;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
const defaultOptions =  {}
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: string;
  String: string;
  Boolean: boolean;
  Int: number;
  Float: number;
};

/** A physical or virtual board game. */
export type BoardGame = {
  __typename?: 'BoardGame';
  /** A long-form description of the game. */
  description?: Maybe<Scalars['String']>;
  id: Scalars['ID'];
  /** The maximum number of players the game supports. */
  max_players?: Maybe<Scalars['Int']>;
  /** The minimum number of players the game supports. */
  min_players?: Maybe<Scalars['Int']>;
  name: Scalars['String'];
  /** Play time, in minutes, for a typical game. */
  play_time?: Maybe<Scalars['Int']>;
  /** A one-line summary of the game. */
  summary?: Maybe<Scalars['String']>;
};

export type Query = {
  __typename?: 'Query';
  /** Access a BoardGame by its unique id, if it exists. */
  game_by_id?: Maybe<BoardGame>;
};


export type QueryGame_By_IdArgs = {
  id?: Maybe<Scalars['ID']>;
};

export type GameByIdQueryVariables = Exact<{
  id?: Maybe<Scalars['ID']>;
}>;


export type GameByIdQuery = (
  { __typename?: 'Query' }
  & { game_by_id?: Maybe<(
    { __typename?: 'BoardGame' }
    & Pick<BoardGame, 'description' | 'id' | 'max_players' | 'min_players' | 'name' | 'play_time' | 'summary'>
  )> }
);


export const GameByIdDocument = gql`
    query GameById($id: ID) {
  game_by_id(id: $id) {
    description
    id
    max_players
    min_players
    name
    play_time
    summary
  }
}
    `;

/**
 * __useGameByIdQuery__
 *
 * To run a query within a React component, call `useGameByIdQuery` and pass it any options that fit your needs.
 * When your component renders, `useGameByIdQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useGameByIdQuery({
 *   variables: {
 *      id: // value for 'id'
 *   },
 * });
 */
export function useGameByIdQuery(baseOptions?: Apollo.QueryHookOptions<GameByIdQuery, GameByIdQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<GameByIdQuery, GameByIdQueryVariables>(GameByIdDocument, options);
      }
export function useGameByIdLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<GameByIdQuery, GameByIdQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<GameByIdQuery, GameByIdQueryVariables>(GameByIdDocument, options);
        }
export type GameByIdQueryHookResult = ReturnType<typeof useGameByIdQuery>;
export type GameByIdLazyQueryHookResult = ReturnType<typeof useGameByIdLazyQuery>;
export type GameByIdQueryResult = Apollo.QueryResult<GameByIdQuery, GameByIdQueryVariables>;